package ru.mintrocket.lib.mintpermissions.internal.statuses

import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private typealias StatusMap = Map<MintPermission, MintPermissionStatus>

class StatusesControllerImpl : StatusesController {

    private val statusFlow = MutableStateFlow<StatusMap>(emptyMap())

    override fun observe(): Flow<Map<MintPermission, MintPermissionStatus>> {
        return statusFlow.asStateFlow()
    }

    override suspend fun updateStatuses(statuses: List<MintPermissionStatus>) {
        statusFlow.update { currentMap ->
            val newMap = currentMap.toMutableMap()
            statuses.forEach {
                newMap[it.permission] = it
            }
            newMap
        }
    }
}