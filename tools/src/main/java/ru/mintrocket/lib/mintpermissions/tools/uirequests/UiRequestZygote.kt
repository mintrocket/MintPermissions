package ru.mintrocket.lib.mintpermissions.tools.uirequests

import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.internal.UiRequestControllerImpl
import ru.mintrocket.lib.mintpermissions.tools.uirequests.internal.UiRequestManager

public class UiRequestZygote<T, R>(
    private val key: String,
    private val consumer: UiRequestConsumer<T, R>,
    private val config: UiRequestConfig = UiRequestConfig()
) {

    private val controllerImpl by lazy { UiRequestControllerImpl<T, R>() }

    public val controller: UiRequestController<T, R> by lazy { controllerImpl }

    public fun createManager(): ManagerInitializer {
        return UiRequestManager(key, config, controllerImpl, consumer)
    }
}