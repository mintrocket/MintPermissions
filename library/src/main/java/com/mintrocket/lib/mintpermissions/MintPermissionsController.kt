package com.mintrocket.lib.mintpermissions

import com.mintrocket.lib.mintpermissions.models.MintPermission
import com.mintrocket.lib.mintpermissions.models.MintPermissionMultipleResult
import com.mintrocket.lib.mintpermissions.models.MintPermissionResult
import com.mintrocket.lib.mintpermissions.models.MintPermissionStatus
import kotlinx.coroutines.flow.Flow

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