package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
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

internal class UiRequestManager<T, R>(
    private val zygoteKey: String,
    private val config: UiRequestConfig,
    private val controller: UiRequestControllerImpl<T, R>,
    private val consumer: UiRequestConsumer<T, R>,
) : ManagerInitializer {

    @Suppress("UNCHECKED_CAST")
    override fun init(activity: ComponentActivity) {
        val viewModel = lazy {
            val factory = UiRequestViewModelFactory(config, controller, activity)
            val provider = ViewModelProvider(activity.viewModelStore, factory)
            provider[zygoteKey, UiRequestViewModel::class.java] as UiRequestViewModel<T, R>
        }.value

        activity.lifecycle.addObserver(ViewModeEnabledObserver(viewModel))
        viewModel.requestFlow
            .filterNotNull()
            .mapLatest { request -> executeRequest(activity, request) }
            .onEach(viewModel::finishRequest)
            .launchIn(activity.lifecycleScope)
    }

    private suspend fun executeRequest(
        activity: ComponentActivity,
        request: UiRequest<T>
    ): UiResult<T, R> {
        val resultData = consumer.request(activity, request)
        return UiResult(request, resultData)
    }
}