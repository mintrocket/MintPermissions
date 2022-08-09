package ru.mintrocket.lib.mintpermissions.ext

import kotlinx.coroutines.flow.Flow
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

public fun MintPermissionsController.observe(vararg permissions: MintPermission): Flow<List<MintPermissionStatus>> {
    return observe(permissions.asList())
}

public suspend fun MintPermissionsController.get(vararg permissions: MintPermission): List<MintPermissionStatus> {
    return get(permissions.asList())
}

public suspend fun MintPermissionsController.request(vararg permissions: MintPermission): List<MintPermissionResult> {
    return request(permissions.asList())
}