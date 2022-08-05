package ru.mintrocket.lib.mintpermissions.tools.uirequests

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.internal.UiRequestControllerImpl
import ru.mintrocket.lib.mintpermissions.tools.uirequests.internal.UiRequestManager
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class UiRequestZygote<T, R>(
    private val key: String,
    private val consumer: UiRequestConsumer<T, R>,
    private val config: UiRequestConfig = UiRequestConfig()
) {

    private val controllerImpl by lazy { UiRequestControllerImpl<T, R>() }

    val controller: UiRequestController<T, R> by lazy { controllerImpl }

    fun createManager(): ManagerInitializer {
        return UiRequestManager(key, config, controllerImpl, consumer)
    }
}