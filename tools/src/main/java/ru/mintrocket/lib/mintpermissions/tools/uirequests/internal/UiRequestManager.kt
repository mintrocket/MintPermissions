package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConfig
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.model.UiResult

internal class UiRequestManager<T, R>(
    private val zygoteKey: String,
    private val config: UiRequestConfig,
    private val controller: UiRequestControllerImpl<T, R>,
    private val consumer: UiRequestConsumer<T, R>,
) : ManagerInitializer {

    override fun init(activity: ComponentActivity) {

        val viewModel = lazy {
            val factory = UiRequestViewModelFactory(config, controller, activity)
            val provider = ViewModelProvider(activity.viewModelStore, factory)
            provider[zygoteKey, UiRequestViewModel::class.java] as UiRequestViewModel<T, R>
        }.value

        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                viewModel.setEnabled(true)
            }

            override fun onPause(owner: LifecycleOwner) {
                super.onPause(owner)
                viewModel.setEnabled(false)
            }
        })
        viewModel
            .requestFlow
            .mapLatest { request ->
                request?.let {
                    val resultData = consumer.request(activity, it)
                    UiResult(it, resultData)
                }
            }
            .filterNotNull()
            .onEach {
                viewModel.finishRequest(it)
            }
            .launchIn(activity.lifecycleScope)
    }
}