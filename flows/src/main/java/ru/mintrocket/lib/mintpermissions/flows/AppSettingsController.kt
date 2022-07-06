package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController

class AppSettingsController(
    private val requestController: UiRequestController<Unit, Unit>,
) {

    suspend fun open() = requestController.request(Unit)
}