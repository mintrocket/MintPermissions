package ru.mintrocket.lib.mintpermissions.tools.uirequests

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.internal.UiRequestControllerImpl
import ru.mintrocket.lib.mintpermissions.tools.uirequests.internal.UiRequestManager

class UiRequestZygote<T, R>(
    private val consumer: UiRequestConsumer<T, R>,
    private val config: UiRequestConfig = UiRequestConfig()
) {

    private val scope by lazy { CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()) }

    private val controllerImpl by lazy { UiRequestControllerImpl<T, R>(scope) }

    val controller: UiRequestController<T, R> by lazy { controllerImpl }

    fun createManager(): ManagerInitializer = UiRequestManager(config, controllerImpl, consumer)
}