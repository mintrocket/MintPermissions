package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult

interface DialogsController {
    suspend fun open(request: DialogRequest): DialogResult
}