package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.flows.internal.FlowConfig
import ru.mintrocket.lib.mintpermissions.flows.internal.FlowResultStatus
import ru.mintrocket.lib.mintpermissions.models.MintPermission

interface MintPermissionsDialogFlow {

    suspend fun request(
        permission: MintPermission,
        config: FlowConfig? = null
    ): FlowResultStatus

    suspend fun request(
        permissions: List<MintPermission>,
        config: FlowConfig? = null
    ): FlowResultStatus
}