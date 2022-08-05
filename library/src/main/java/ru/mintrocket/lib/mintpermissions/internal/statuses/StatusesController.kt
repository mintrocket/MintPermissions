package ru.mintrocket.lib.mintpermissions.internal.statuses

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

internal interface StatusesController {

    fun observe(): Flow<Map<MintPermission, MintPermissionStatus>>

    suspend fun updateStatuses(statuses: List<MintPermissionStatus>)

    fun reset()
}

internal fun StatusesController(): StatusesController = StatusesControllerImpl()

private typealias StatusMap = Map<MintPermission, MintPermissionStatus>

private class StatusesControllerImpl : StatusesController {

    private val statusFlow = MutableStateFlow<StatusMap?>(null)

    override fun observe(): Flow<Map<MintPermission, MintPermissionStatus>> {
        return statusFlow.filterNotNull()
    }

    override suspend fun updateStatuses(statuses: List<MintPermissionStatus>) {
        statusFlow.update { currentMap ->
            currentMap.orEmpty() + statuses.associateBy { it.permission }
        }
    }

    override fun reset() {
        statusFlow.value = null
    }
}