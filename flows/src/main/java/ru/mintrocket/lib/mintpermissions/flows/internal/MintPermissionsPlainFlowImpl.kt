package ru.mintrocket.lib.mintpermissions.flows.internal

import android.util.Log
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterNotGranted
import ru.mintrocket.lib.mintpermissions.ext.isAllGranted
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsDialogFlow
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsPlainFlow
import ru.mintrocket.lib.mintpermissions.models.MintPermission

internal class MintPermissionsPlainFlowImpl(
    private val permissions: List<MintPermission>,
    private val flowConfig: FlowConfig,
    private val permissionsController: MintPermissionsController,
    private val dialogFlow: MintPermissionsDialogFlow
) : MintPermissionsPlainFlow {

    override val isAllGrantedFlow = permissionsController
        .observe(permissions)
        .map { it.isAllGranted() }
        .distinctUntilChanged()

    override val notGrantedFlow = permissionsController
        .observe(permissions)
        .map { it.filterNotGranted() }
        .distinctUntilChanged()

    override val firstNotGrantedFlow = notGrantedFlow
        .map { it.firstOrNull() }
        .distinctUntilChanged()

    override suspend fun initialRequest() {
        permissionsController.request(permissions)
    }

    override suspend fun request(): FlowResultStatus {
        return dialogFlow.request(permissions, flowConfig)
    }

    override suspend fun requestSequentially(): FlowResultStatus {
        Log.e("kekeke", "requestSequentially $permissions with $flowConfig")
        permissions.forEach {
            val result = dialogFlow.request(it, flowConfig)
            if (result == FlowResultStatus.CANCELED) {
                return FlowResultStatus.CANCELED
            }
        }
        return FlowResultStatus.SUCCESS
    }

}