package ru.mintrocket.lib.mintpermissions.internal.statuses

import kotlinx.coroutines.flow.Flow
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

interface StatusesController {

    fun observe(): Flow<Map<MintPermission, MintPermissionStatus>>

    suspend fun updateStatuses(statuses: List<MintPermissionStatus>)

    fun reset()
}