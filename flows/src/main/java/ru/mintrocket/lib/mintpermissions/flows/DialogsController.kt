package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.model.DialogResult
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController

class DialogsController(
    private val requestController: UiRequestController<DialogRequest, DialogResult>
) {

    suspend fun open(request: DialogRequest): DialogResult = requestController.request(request)
}