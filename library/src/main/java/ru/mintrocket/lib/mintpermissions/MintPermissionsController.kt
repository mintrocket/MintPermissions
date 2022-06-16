package ru.mintrocket.lib.mintpermissions

import kotlinx.coroutines.flow.Flow
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionMultipleResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

interface MintPermissionsController {

    fun observe(permission: MintPermission): Flow<MintPermissionStatus>

    fun observe(permissions: List<MintPermission>): Flow<List<MintPermissionStatus>>

    fun observeAll(): Flow<List<MintPermissionStatus>>

    suspend fun getAll(): List<MintPermissionStatus>

    suspend fun get(permission: MintPermission): MintPermissionStatus

    suspend fun get(permissions: List<MintPermission>): List<MintPermissionStatus>

    suspend fun request(permissions: List<MintPermission>): MintPermissionMultipleResult

    suspend fun request(permission: MintPermission): MintPermissionResult
}