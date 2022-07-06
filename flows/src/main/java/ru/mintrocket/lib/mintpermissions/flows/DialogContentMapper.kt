package ru.mintrocket.lib.mintpermissions.flows

import android.content.Context
import ru.mintrocket.lib.mintpermissions.flows.model.DialogContent
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequest

interface DialogContentMapper {

    suspend fun map(context: Context, request: DialogRequest): DialogContent
}