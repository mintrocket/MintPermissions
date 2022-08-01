package ru.mintrocket.lib.mintpermissions.flows.ui

import android.content.Context
import ru.mintrocket.lib.mintpermissions.flows.models.DialogContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest

class DefaultDialogRequestMapperImpl : DialogContentMapper {

    override suspend fun map(context: Context, request: DialogRequest): DialogContent {
        return DialogContent(
            "Some title",
            "Request ${request.group} with results ${request.results.map { it.status }}",
            "Action",
            "Cancel"
        )
    }
}