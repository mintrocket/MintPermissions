package ru.mintrocket.lib.mintpermissions.flows.model

import ru.mintrocket.lib.mintpermissions.flows.DialogContentConsumer
import ru.mintrocket.lib.mintpermissions.flows.DialogContentMapper
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