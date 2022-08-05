package ru.mintrocket.lib.mintpermissions.flows.internal

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterNotGranted
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsDialogFlow
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsPlainFlow
import ru.mintrocket.lib.mintpermissions.flows.models.FlowConfig
import ru.mintrocket.lib.mintpermissions.flows.models.FlowResultStatus
import ru.mintrocket.lib.mintpermissions.models.MintPermission

internal class MintPermissionsPlainFlowImpl(
    private val permissions: List<MintPermission>,
    private val defaultFlowConfig: FlowConfig,
    private val permissionsController: MintPermissionsController,
    private val dialogFlow: MintPermissionsDialogFlow
) : MintPermissionsPlainFlow {

    override fun observeNotGranted() = permissionsController
        .observe(permissions)
        .map { it.filterNotGranted() }
        .distinctUntilChanged()

    override fun observeFirstNotGranted() = observeNotGranted()
        .map { it.firstOrNull() }
        .distinctUntilChanged()

    override suspend fun request(config: FlowConfig?): FlowResultStatus {
        return dialogFlow.request(permissions, config ?: defaultFlowConfig)
    }

    override suspend fun requestSequentially(config: FlowConfig?): FlowResultStatus {
        permissions.forEach {
            val result = dialogFlow.request(it, config ?: defaultFlowConfig)
            if (result == FlowResultStatus.CANCELED) {
                return FlowResultStatus.CANCELED
            }
        }
        return FlowResultStatus.SUCCESS
    }

}