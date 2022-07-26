package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController

class DialogsController(
    private val requestController: UiRequestController<DialogRequest, DialogResult>
) {

    suspend fun open(request: DialogRequest): DialogResult = requestController.request(request)
}