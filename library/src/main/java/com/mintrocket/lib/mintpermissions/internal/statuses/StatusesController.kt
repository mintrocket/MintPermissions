package com.mintrocket.lib.mintpermissions.internal.statuses

import com.mintrocket.lib.mintpermissions.models.MintPermission
import com.mintrocket.lib.mintpermissions.models.MintPermissionStatus
import kotlinx.coroutines.flow.Flow

interface StatusesController {

    fun observe(): Flow<Map<MintPermission, MintPermissionStatus>>

    suspend fun updateStatuses(statuses: List<MintPermissionStatus>)
}