package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

interface MintPermissionsDialogFlow {

    suspend fun request(
        permission: MintPermission,
        config: FlowConfig? = null
    ): FlowResult

    suspend fun request(
        permissions: List<MintPermission>,
        config: FlowConfig? = null
    ): FlowMultipleResult
}