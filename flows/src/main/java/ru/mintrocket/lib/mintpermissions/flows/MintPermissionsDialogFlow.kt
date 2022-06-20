package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

interface MintPermissionsDialogFlow {

    suspend fun request(permissions: List<MintPermission>): List<MintPermissionStatus>
}