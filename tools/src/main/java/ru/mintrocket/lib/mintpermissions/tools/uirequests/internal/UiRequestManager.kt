package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConfig
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiResult

internal class UiRequestManager<T : Parcelable, R>(
    private val zygoteKey: String,
    private val config: UiRequestConfig,
    private val controller: UiRequestControllerImpl<T, R>,
    private val consumer: UiRequestConsumer<T, R>,
) : ManagerInitializer {

    private companion object {
        const val KEY_REQUESTS = "requests"
    }

    override fun init(activity: ComponentActivity) {
        val viewModel = UiRequestViewModel(controller)
        if (config.saveQueueState) {
            initSavedState(activity, viewModel)
        }

        activity.lifecycle.addObserver(ViewModelLifecycleObserver(viewModel))
        viewModel.requestFlow
            .mapLatest { request ->
                if (request != null) {
                    executeRequest(activity, request)
                } else {
                    null
                }
            }
            .filterNotNull()
            .onEach(viewModel::finishRequest)
            .launchIn(activity.lifecycleScope)
    }

    private fun initSavedState(activity: ComponentActivity, viewModel: UiRequestViewModel<T, R>) {
        activity.savedStateRegistry.registerSavedStateProvider(zygoteKey) {
            Bundle().apply {
                putParcelableArrayList(KEY_REQUESTS, ArrayList(viewModel.getRequestQueue()))
            }
        }
        val savedRequests = activity.savedStateRegistry.consumeRestoredStateForKey(zygoteKey)
            ?.getParcelableArrayList<UiRequest<T>>(KEY_REQUESTS)

        viewModel.restoreRequestQueue(savedRequests.orEmpty())
    }

    private suspend fun executeRequest(
        activity: ComponentActivity,
        request: UiRequest<T>
    ): UiResult<T, R> {
        val resultData = consumer.request(activity, request)
        return UiResult(request, resultData)
    }
}