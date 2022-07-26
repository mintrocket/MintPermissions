package ru.mintrocket.lib.mintpermissions.flows.internal

import ru.mintrocket.lib.mintpermissions.flows.AppSettingsController
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController

internal class AppSettingsControllerImpl(
    private val requestController: UiRequestController<Unit, Unit>,
) : AppSettingsController {

    override suspend fun openSettings() = requestController.request(Unit)
}