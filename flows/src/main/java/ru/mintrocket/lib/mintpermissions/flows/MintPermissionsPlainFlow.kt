package ru.mintrocket.lib.mintpermissions.flows

import kotlinx.coroutines.flow.Flow
import ru.mintrocket.lib.mintpermissions.flows.internal.FlowResultStatus
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

interface MintPermissionsPlainFlow {
    val isAllGrantedFlow: Flow<Boolean>
    val notGrantedFlow: Flow<List<MintPermissionStatus>>
    val firstNotGrantedFlow: Flow<MintPermissionStatus?>

    suspend fun initialRequest()

    suspend fun request(): FlowResultStatus

    suspend fun requestSequentially(): FlowResultStatus
}