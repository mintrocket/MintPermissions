package ru.mintrocket.lib.mintpermissions.flows.ext

import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsDialogFlow
import ru.mintrocket.lib.mintpermissions.flows.models.FlowConfig
import ru.mintrocket.lib.mintpermissions.flows.models.FlowResultStatus
import ru.mintrocket.lib.mintpermissions.models.MintPermission

public suspend fun MintPermissionsDialogFlow.request(
    vararg permissions: MintPermission,
    config: FlowConfig?
): FlowResultStatus {
    return request(permissions.toList(), config)
}