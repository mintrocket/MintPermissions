package ru.mintrocket.lib.mintpermissions.flows.internal

import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterDenied
import ru.mintrocket.lib.mintpermissions.ext.filterNeedsRationale
import ru.mintrocket.lib.mintpermissions.ext.isDenied
import ru.mintrocket.lib.mintpermissions.ext.isNeedsRationale
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsDialogFlow
import ru.mintrocket.lib.mintpermissions.flows.ext.isCancel
import ru.mintrocket.lib.mintpermissions.flows.ext.isCanceled
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
        if (safeRationaleResult.isCanceled()) {
            return FlowResultStatus.CANCELED
        }

        if (config.showGroupedByStatus) {
            val result = requestGroup(config, permissions) {
                it.isNeedsRationale()
            }
            if (result.isCanceled()) {
                return FlowResultStatus.CANCELED
            }
        }

        if (config.showGroupedByStatus) {
            val result = requestGroup(config, permissions) {
                it.isDenied()
            }
            if (result.isCanceled()) {
                return FlowResultStatus.CANCELED
            }
        }

        return requestInner(config, permissions)
    }

    private suspend fun requestGroup(
        config: FlowConfig,
        permissions: List<MintPermission>,
        groupFilter: (MintPermissionStatus) -> Boolean
    ): FlowResultStatus {
        val group = permissionsController
            .get(permissions)
            .filter(groupFilter::invoke)
            .map(MintPermissionStatus::permission)
        return requestInner(config, group)
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

        if (dialogResult.isCancel()) {
            return FlowResultStatus.CANCELED
        }
        return requestInner(config, permissions)
    }

    private suspend fun handleDenied(
        config: FlowConfig,
        deniedResults: List<MintPermissionResult>,
        permissions: List<MintPermission>
    ): FlowResultStatus {
        val checkerConfig = if (config.checkBeforeSettings) {
            config.copy(checkBeforeSettings = false)
        } else {
            config
        }
        val request = DialogRequest(
            group = DialogRequestGroup.DENIED,
            results = deniedResults,
            contentMapper = config.contentMapper,
            contentConsumer = config.contentConsumer
        )
        val dialogResult = dialogsController.open(request)

        if (dialogResult.isCancel()) {
            return FlowResultStatus.CANCELED
        }
        if (!config.checkBeforeSettings) {
            appSettingsController.open()
        }
        return requestInner(checkerConfig, permissions)
    }

}