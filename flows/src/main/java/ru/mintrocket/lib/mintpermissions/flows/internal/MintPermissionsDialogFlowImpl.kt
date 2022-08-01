package ru.mintrocket.lib.mintpermissions.flows.internal

import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterDenied
import ru.mintrocket.lib.mintpermissions.ext.filterNeedsRationale
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsDialogFlow
import ru.mintrocket.lib.mintpermissions.flows.models.*
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult

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
            val rationale = permissionsController
                .get(permissions)
                .filterNeedsRationale()
                .map { it.permission }
            val result = requestInner(config, rationale)

            if (result == FlowResultStatus.CANCELED) {
                return FlowResultStatus.CANCELED
            }
        }

        if (config.showGroupedByStatus) {
            val denied = permissionsController
                .get(permissions)
                .filterDenied()
                .map { it.permission }
            val result = requestInner(config, denied)

            if (result == FlowResultStatus.CANCELED) {
                return FlowResultStatus.CANCELED
            }
        }

        return requestInner(config, permissions)
    }

    private suspend fun requestInner(
        config: FlowConfig,
        permissions: List<MintPermission>
    ): FlowResultStatus {
        val permissionsResult = permissionsController.request(permissions)

        val rationale = permissionsResult.filterNeedsRationale()
        val denied = permissionsResult.filterDenied()

        return when {
            rationale.isNotEmpty() -> handleRationale(config, rationale, permissions)
            denied.isNotEmpty() -> handleDenied(config, denied, permissions)
            else -> FlowResultStatus.SUCCESS
        }
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
        val dialogResult = if (config.showNeedsRationale) {
            val request = DialogRequest(
                group = DialogRequestGroup.NEEDS_RATIONALE,
                results = rationaleResults,
                contentMapper = config.contentMapper,
                contentConsumer = config.contentConsumer
            )
            dialogsController.open(request)
        } else {
            DialogResult.CANCEL
        }
        return if (dialogResult == DialogResult.ACTION) {
            requestInner(config, permissions)
        } else {
            FlowResultStatus.CANCELED
        }
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