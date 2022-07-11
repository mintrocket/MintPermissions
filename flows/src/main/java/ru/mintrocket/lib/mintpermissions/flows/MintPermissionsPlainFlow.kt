package ru.mintrocket.lib.mintpermissions.flows

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterNotGranted
import ru.mintrocket.lib.mintpermissions.ext.isAllGranted
import ru.mintrocket.lib.mintpermissions.models.MintPermission

class MintPermissionsPlainFlow(
    private val permissions: List<MintPermission>,
    private val flowConfig: FlowConfig,
    private val permissionsController: MintPermissionsController,
    private val dialogFlow: MintPermissionsDialogFlow
) {

    val isAllGrantedFlow = permissionsController
        .observe(permissions)
        .map { it.isAllGranted() }
        .distinctUntilChanged()

    val notGrantedFlow = permissionsController
        .observe(permissions)
        .map { it.filterNotGranted() }
        .distinctUntilChanged()

    val firstNotGrantedFlow = notGrantedFlow
        .map { it.firstOrNull() }
        .distinctUntilChanged()

    suspend fun initialRequest() {
        permissionsController.request(permissions)
    }

    suspend fun requestNext() {
        dialogFlow.request(permissions, flowConfig)
    }

}