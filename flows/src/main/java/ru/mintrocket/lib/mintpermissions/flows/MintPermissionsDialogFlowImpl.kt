package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterDenied
import ru.mintrocket.lib.mintpermissions.ext.filterNeedsRationale
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequestType
import ru.mintrocket.lib.mintpermissions.flows.model.DialogResult
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult

data class FlowConfig(
    val showNeedsRationale: Boolean = true,
    val checkBeforeSettings: Boolean = true,
    val customContentMapper: DialogContentMapper? = null,
    val customContentConsumer: DialogContentConsumer? = null
)

enum class FlowResultStatus {
    CANCELED,
    SUCCESS
}

data class FlowMultipleResult(
    val status: FlowResultStatus,
    val permissionResults: List<MintPermissionResult>
)

data class FlowResult(
    val status: FlowResultStatus,
    val permissionResult: MintPermissionResult
)

class MintPermissionsDialogFlowImpl(
    private val defaultFlowConfig: FlowConfig,
    private val permissionsController: MintPermissionsController,
    private val dialogsController: DialogsController,
    private val appSettingsController: AppSettingsController
) : MintPermissionsDialogFlow {

    override suspend fun request(
        permission: MintPermission,
        config: FlowConfig?
    ): FlowResult {
        return request(listOf(permission)).let {
            FlowResult(it.status, it.permissionResults.first())
        }
    }

    override suspend fun request(
        permissions: List<MintPermission>,
        config: FlowConfig?
    ): FlowMultipleResult {
        val innerConfig = config ?: defaultFlowConfig
        return requestInner(0, innerConfig, permissions)
    }

    private suspend fun requestInner(
        level: Int,
        config: FlowConfig,
        permissions: List<MintPermission>
    ): FlowMultipleResult {
        val permissionsResult = permissionsController.request(permissions)
        var flowResult = FlowMultipleResult(FlowResultStatus.SUCCESS, permissionsResult)

        val rationale = if (config.showNeedsRationale) {
            permissionsResult.filterNeedsRationale()
        } else {
            emptyList()
        }
        val denied = permissionsResult.filterDenied()

        flowResult = when {
            rationale.isNotEmpty() -> {
                val request = DialogRequest(
                    DialogRequestType.NEEDS_RATIONALE,
                    rationale,
                    config.customContentMapper,
                    config.customContentConsumer
                )
                val dialogResult = dialogsController.open(request)
                if (dialogResult == DialogResult.ACTION) {
                    requestInner(level + 1, config, permissions)
                } else {
                    flowResult.copy(status = FlowResultStatus.CANCELED)
                }
            }
            denied.isNotEmpty() -> {
                val request = DialogRequest(
                    DialogRequestType.DENIED,
                    denied,
                    config.customContentMapper,
                    config.customContentConsumer
                )
                val dialogResult = dialogsController.open(request)
                if (dialogResult == DialogResult.ACTION) {
                    if (config.checkBeforeSettings) {
                        requestInner(
                            level + 1,
                            config.copy(checkBeforeSettings = false),
                            permissions
                        )
                    } else {
                        appSettingsController.open()
                        requestInner(level + 1, config, permissions)
                    }
                } else {
                    flowResult.copy(status = FlowResultStatus.CANCELED)
                }
            }
            else -> flowResult
        }
        return flowResult
    }
}