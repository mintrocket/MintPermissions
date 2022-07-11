package ru.mintrocket.lib.mintpermissions.flows

import android.util.Log
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
        permissionsController.request(permissions).also {
            Log.w("kekeke", "MintPermissionsPlainFlow $it")
        }
    }

    suspend fun request(): FlowResultStatus {
        return dialogFlow.request(permissions, flowConfig).status
    }

    suspend fun requestSequentially(): FlowResultStatus {
        permissions.forEach {
            val result = dialogFlow.request(it, flowConfig)
            if (result.status == FlowResultStatus.CANCELED) {
                return FlowResultStatus.CANCELED
            }
        }
        return FlowResultStatus.SUCCESS
    }

}