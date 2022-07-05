package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConfig
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.model.UiResult

internal class UiRequestManager<T, R>(
    private val config: UiRequestConfig,
    private val controller: UiRequestControllerImpl<T, R>,
    private val consumer: UiRequestConsumer<T, R>,
) : ManagerInitializer {

    override fun init(activity: ComponentActivity) {
        val viewModelFactory by lazy {
            UiRequestViewModelFactory(config, controller, activity)
        }
        val viewModel by lazy {
            activity.viewModels<UiRequestViewModel<T, R>> { viewModelFactory }.value
        }
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
            .map {
                val resultData = consumer.request(activity, it)
                UiResult(it, resultData)
            }
            .onEach {
                viewModel.finishRequest(it.request)
                controller.sendResult(it)
            }
            .launchIn(activity.lifecycleScope)
    }
}