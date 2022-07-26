package ru.mintrocket.lib.mintpermissions.flows

import android.util.Log
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterDenied
import ru.mintrocket.lib.mintpermissions.ext.filterNeedsRationale
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequestType
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult

data class FlowConfig(
    val showGroupedByStatus: Boolean = true,
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
    ): FlowResultStatus {
        return request(listOf(permission), config)
    }

    override suspend fun request(
        permissions: List<MintPermission>,
        config: FlowConfig?
    ): FlowResultStatus {
        val innerConfig = config ?: defaultFlowConfig
        return requestWithoutRationale(permissions, innerConfig)
    }

    private suspend fun requestWithoutRationale(
        permissions: List<MintPermission>,
        config: FlowConfig
    ): FlowResultStatus {
        if (config.showGroupedByStatus) {
            val rationale = permissionsController
                .get(permissions)
                .filterNeedsRationale()
                .map { it.permission }
            val result = requestInner(config, rationale)

            Log.e("kekeke", "group rationale res $result")

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
            Log.e("kekeke", "group denied res $result")

            if (result == FlowResultStatus.CANCELED) {
                return FlowResultStatus.CANCELED
            }
        }

        return requestInner(config, permissions).also {
            Log.e("kekeke", "group full res $it")
        }
    }

    private suspend fun requestInner(
        config: FlowConfig,
        permissions: List<MintPermission>
    ): FlowResultStatus {
        Log.e("kekeke", "requestInner $permissions with $config")
        val permissionsResult = permissionsController.request(permissions)

        val rationale = permissionsResult.filterNeedsRationale()
        val denied = permissionsResult.filterDenied()

        return when {
            rationale.isNotEmpty() -> handleRationale(config, rationale, permissions)
            denied.isNotEmpty() -> handleDenied(config, denied, permissions)
            else -> FlowResultStatus.SUCCESS
        }
    }

    private suspend fun handleRationale(
        config: FlowConfig,
        rationaleResults: List<MintPermissionResult>,
        permissions: List<MintPermission>
    ): FlowResultStatus {
        val request = DialogRequest(
            type = DialogRequestType.NEEDS_RATIONALE,
            results = rationaleResults,
            customContentMapper = config.customContentMapper,
            customContentConsumer = config.customContentConsumer
        )
        val dialogResult = if (config.showNeedsRationale) {
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
            type = DialogRequestType.DENIED,
            results = deniedResults,
            customContentMapper = config.customContentMapper,
            customContentConsumer = config.customContentConsumer
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