package ru.mintrocket.lib.mintpermissions.internal.statuses

import android.util.Log
import kotlinx.coroutines.flow.*
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

private typealias StatusMap = Map<MintPermission, MintPermissionStatus>

class StatusesControllerImpl : StatusesController {

    private val statusFlow = MutableStateFlow<StatusMap?>(null)

    override fun observe(): Flow<Map<MintPermission, MintPermissionStatus>> {
        return statusFlow.asStateFlow().filterNotNull()
    }

    override suspend fun updateStatuses(statuses: List<MintPermissionStatus>) {
        statusFlow.update { currentMap ->
            val newMap = currentMap.orEmpty().toMutableMap()
            statuses.forEach {
                newMap[it.permission] = it
            }
            newMap
        }
    }

    override fun reset() {
        statusFlow.value = null
    }
}