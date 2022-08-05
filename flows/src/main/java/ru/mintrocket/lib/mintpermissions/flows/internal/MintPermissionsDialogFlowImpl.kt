package ru.mintrocket.lib.mintpermissions.flows.internal

import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterDenied
import ru.mintrocket.lib.mintpermissions.ext.filterNeedsRationale
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsDialogFlow
import ru.mintrocket.lib.mintpermissions.flows.models.*
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

internal class MintPermissionsDialogFlowImpl(
    private val defaultFlowConfig: FlowConfig,
    private val permissionsController: MintPermissionsController,
    private val dialogsController: DialogsControllerImpl,
    private val appSettingsController: AppSettingsControllerImpl
) : MintPermissionsDialogFlow {

    override suspend fun request(
        permission: MintPermission,
        config: FlowConfig?
    ): FlowResultStatus {
        return request(listOf(permission), config)
    }

    override suspend fun request(
        permissions: List<MintPermission>,
        config: FlowConfig?
    ): FlowResultStatus {
        val innerConfig = config ?: defaultFlowConfig
        return requestWithRationaleCheck(permissions, innerConfig)
    }

    private suspend fun requestWithRationaleCheck(
        permissions: List<MintPermission>,
        config: FlowConfig
    ): FlowResultStatus {
        val safeRationaleResult = safeRationale(config, permissions)
        if (safeRationaleResult == FlowResultStatus.CANCELED) {
            return FlowResultStatus.CANCELED
        }

        if (config.showGroupedByStatus) {
            val rationale = needRationalePermissions(permissions)
            if (requestInner(config, rationale) == FlowResultStatus.CANCELED) {
                return FlowResultStatus.CANCELED
            }
        }

        if (config.showGroupedByStatus) {
            val denied = deniedPermissions(permissions)
            if (requestInner(config, denied) == FlowResultStatus.CANCELED) {
                return FlowResultStatus.CANCELED
            }
        }

        return requestInner(config, permissions)
    }

    private suspend fun deniedPermissions(permissions: List<MintPermission>): List<MintPermission> {
        return permissionsController
            .get(permissions)
            .filterDenied()
            .map(MintPermissionStatus::permission)
    }

    private suspend fun needRationalePermissions(permissions: List<MintPermission>): List<MintPermission> {
        return permissionsController
            .get(permissions)
            .filterNeedsRationale()
            .map(MintPermissionStatus::permission)
    }

    private suspend fun requestInner(
        config: FlowConfig,
        permissions: List<MintPermission>
    ): FlowResultStatus {
        val permissionsResult = permissionsController.request(permissions)

        val rationale = permissionsResult.filterNeedsRationale()
        if (rationale.isNotEmpty()) {
            return handleRationale(config, rationale, permissions)
        }

        val denied = permissionsResult.filterDenied()
        if (denied.isNotEmpty()) {
            return handleDenied(config, denied, permissions)
        }

        return FlowResultStatus.SUCCESS
    }

    private suspend fun safeRationale(
        config: FlowConfig,
        permissions: List<MintPermission>
    ): FlowResultStatus {
        val needsRationale = permissionsController.get(permissions).filterNeedsRationale()
        return if (config.showNeedsRationale && needsRationale.isNotEmpty()) {
            val fakeResults = needsRationale.map {
                MintPermissionResult(it, null)
            }
            handleRationale(config, fakeResults, permissions)
        } else {
            FlowResultStatus.SUCCESS
        }
    }

    private suspend fun handleRationale(
        config: FlowConfig,
        rationaleResults: List<MintPermissionResult>,
        permissions: List<MintPermission>
    ): FlowResultStatus {
        val dialogResult: DialogResult
        if (config.showNeedsRationale) {
            val request = DialogRequest(
                group = DialogRequestGroup.NEEDS_RATIONALE,
                results = rationaleResults,
                contentMapper = config.contentMapper,
                contentConsumer = config.contentConsumer
            )
            dialogResult = dialogsController.open(request)
        } else {
            dialogResult = DialogResult.CANCEL
        }

        if (dialogResult == DialogResult.ACTION) return requestInner(config, permissions)
        return FlowResultStatus.CANCELED
    }

    private suspend fun handleDenied(
        config: FlowConfig,
        deniedResults: List<MintPermissionResult>,
        permissions: List<MintPermission>
    ): FlowResultStatus {
        val request = DialogRequest(
            group = DialogRequestGroup.DENIED,
            results = deniedResults,
            contentMapper = config.contentMapper,
            contentConsumer = config.contentConsumer
        )
        val dialogResult = dialogsController.open(request)
        return if (dialogResult == DialogResult.ACTION) {
            if (config.checkBeforeSettings) {
                val checkerConfig = config.copy(checkBeforeSettings = false)
                requestInner(checkerConfig, permissions)
            } else {
                appSettingsController.open()
                requestInner(config, permissions)
            }
        } else {
            FlowResultStatus.CANCELED
        }
    }

}