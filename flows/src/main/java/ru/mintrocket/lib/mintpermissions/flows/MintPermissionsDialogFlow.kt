package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.flows.models.FlowConfig
import ru.mintrocket.lib.mintpermissions.flows.models.FlowResultStatus
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