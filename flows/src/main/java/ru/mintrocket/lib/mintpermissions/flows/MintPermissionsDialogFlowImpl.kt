package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterDenied
import ru.mintrocket.lib.mintpermissions.ext.filterNeedsRationale
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

data class FlowConfig(
    val checkBeforeSettings: Boolean
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
    private val dialogsController: DialogsController,
    private val permissionsController: MintPermissionsController,
    private val appSettingsLauncher: AppSettingsLauncher
) : MintPermissionsDialogFlow {

    override suspend fun request(permissions: List<MintPermission>): List<MintPermissionStatus> {
        val config = FlowConfig(checkBeforeSettings = true)
        return requestInner(0, config, permissions).permissionResults.map { it.status }
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
                val dialogResult = dialogsController.showDialog(rationale)
                if (dialogResult == DialogResult.ACTION) {
                    requestInner(level + 1, config, permissions)
                } else {
                    flowResult.copy(status = FlowResultStatus.CANCELED)
                }
            }
            denied.isNotEmpty() -> {
                val dialogResult = dialogsController.showDialog(denied)
                if (dialogResult == DialogResult.ACTION) {
                    if (config.checkBeforeSettings) {
                        requestInner(
                            level + 1,
                            config.copy(checkBeforeSettings = false),
                            permissions
                        )
                    } else {
                        appSettingsLauncher.launchAppSettings()
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