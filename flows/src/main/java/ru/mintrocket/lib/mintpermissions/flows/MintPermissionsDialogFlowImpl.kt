package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterDenied
import ru.mintrocket.lib.mintpermissions.ext.filterNeedsRationale
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequestType
import ru.mintrocket.lib.mintpermissions.flows.model.DialogResult
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

data class FlowConfig(
    val checkBeforeSettings: Boolean = true,
    val customContentMapper: DialogContentMapper? = null,
    val customContentConsumer: DialogContentConsumer? = null
)

enum class FlowResultStatus {
    CANCELED,
    SUCCESS
}

data class FlowResult(
    val status: FlowResultStatus,
    val permissionResults: List<MintPermissionResult>
)

class MintPermissionsDialogFlowImpl(
    private val permissionsController: MintPermissionsController,
    private val dialogsController: DialogsController,
    private val appSettingsController: AppSettingsController
) : MintPermissionsDialogFlow {

    override suspend fun request(
        permission: MintPermission,
        config: FlowConfig?
    ): MintPermissionStatus {
        return request(listOf(permission)).first()
    }

    override suspend fun request(
        permissions: List<MintPermission>,
        config: FlowConfig?
    ): List<MintPermissionStatus> {
        val innerConfig = config ?: FlowConfig()
        return requestInner(0, innerConfig, permissions).permissionResults.map { it.status }
    }

    private suspend fun requestInner(
        level: Int,
        config: FlowConfig,
        permissions: List<MintPermission>
    ): FlowResult {
        var permissionsResult = permissionsController.request(permissions)
        var flowResult = FlowResult(FlowResultStatus.SUCCESS, permissionsResult)


        val rationale = permissionsResult.filterNeedsRationale()
        val denied = permissionsResult.filterDenied()

        flowResult = when {
            rationale.isNotEmpty() -> {
                val request = DialogRequest(DialogRequestType.NEEDS_RATIONALE, rationale, config)
                val dialogResult = dialogsController.open(request)
                if (dialogResult == DialogResult.ACTION) {
                    requestInner(level + 1, config, permissions)
                } else {
                    flowResult.copy(status = FlowResultStatus.CANCELED)
                }
            }
            denied.isNotEmpty() -> {
                val request = DialogRequest(DialogRequestType.DENIED, denied, config)
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