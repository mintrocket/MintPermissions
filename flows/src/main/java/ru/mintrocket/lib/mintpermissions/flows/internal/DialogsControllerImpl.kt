package ru.mintrocket.lib.mintpermissions.flows.internal

import ru.mintrocket.lib.mintpermissions.flows.DialogsController
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController

internal class DialogsControllerImpl(
    private val requestController: UiRequestController<DialogRequest, DialogResult>
) : DialogsController {

    override suspend fun open(request: DialogRequest): DialogResult =
        requestController.request(request)
}