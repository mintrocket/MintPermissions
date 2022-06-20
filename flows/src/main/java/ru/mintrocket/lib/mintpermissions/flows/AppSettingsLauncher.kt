package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController

class AppSettingsLauncher(
    private val controller: UiRequestController<Unit, Unit>
) {

    suspend fun launchAppSettings() = controller.request(Unit)
}