package ru.mintrocket.lib.mintpermissions.flows.ui

import android.content.Context
import ru.mintrocket.lib.mintpermissions.flows.models.DialogContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest

interface DialogContentMapper {

    suspend fun map(context: Context, request: DialogRequest): DialogContent
}