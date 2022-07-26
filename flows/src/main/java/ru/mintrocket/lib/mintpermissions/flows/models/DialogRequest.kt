package ru.mintrocket.lib.mintpermissions.flows.models

import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentConsumer
import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentMapper
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult

data class DialogRequest(
    val type: DialogRequestType,
    val results: List<MintPermissionResult>,
    val customContentMapper: DialogContentMapper?,
    val customContentConsumer: DialogContentConsumer?
)

enum class DialogRequestType {
    NEEDS_RATIONALE,
    DENIED,
    ALL
}