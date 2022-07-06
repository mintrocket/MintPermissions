package ru.mintrocket.lib.mintpermissions.flows.model

import ru.mintrocket.lib.mintpermissions.flows.FlowConfig
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult

data class DialogRequest(
    val type: DialogRequestType,
    val results: List<MintPermissionResult>,
    val config: FlowConfig
)

enum class DialogRequestType {
    NEEDS_RATIONALE,
    DENIED,
    ALL
}