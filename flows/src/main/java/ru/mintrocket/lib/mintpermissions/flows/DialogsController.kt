package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController

class DialogsController(
    private val controllerImpl: UiRequestController<List<MintPermissionResult>, DialogResult>
) {

    suspend fun showDialog(results: List<MintPermissionResult>): DialogResult {
        return controllerImpl.request(results)
    }
}