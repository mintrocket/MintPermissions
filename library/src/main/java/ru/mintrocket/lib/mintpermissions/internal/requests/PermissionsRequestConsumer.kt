package ru.mintrocket.lib.mintpermissions.internal.requests

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import ru.mintrocket.lib.mintpermissions.ext.isDenied
import ru.mintrocket.lib.mintpermissions.ext.isGranted
import ru.mintrocket.lib.mintpermissions.ext.isNeedsRationale
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusProvider
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionAction
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus
import ru.mintrocket.lib.mintpermissions.tools.ext.awaitActivityResult
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest

internal class PermissionsRequestConsumer(
    private val statusProvider: StatusProvider,
) : UiRequestConsumer<List<MintPermission>, List<MintPermissionResult>> {

    override suspend fun request(
        activity: ComponentActivity,
        request: UiRequest<List<MintPermission>>
    ): List<MintPermissionResult> {
        val oldMap = statusProvider
            .getStatuses(activity, request.data)
            .associateBy { it.permission }

        val contract = ActivityResultContracts.RequestMultiplePermissions()
        val input = request.data.toTypedArray()
        activity.awaitActivityResult(contract, request.key, input) { resultMap ->
            // The filter handles the case when if you make a request for permissions and rotate the
            // screen, then invalid data may be returned. In this case, if the user gives permission,
            // then valid data will be returned.
            !(request.data.isNotEmpty() && resultMap.isEmpty())
        }

        return computeResults(activity, request, oldMap)
    }

    private fun computeResults(
        activity: ComponentActivity,
        request: UiRequest<List<MintPermission>>,
        oldMap: Map<MintPermission, MintPermissionStatus>
    ): List<MintPermissionResult> {
        val statuses = statusProvider.getStatuses(activity, request.data)
        val newMap = statuses.associateBy { it.permission }

        return statuses.map { status ->
            val permission = status.permission
            val old = oldMap.getValue(permission)
            val new = newMap.getValue(permission)
            val action = computeAction(permission, old, new)
            MintPermissionResult(status, action)
        }
    }

    private fun computeAction(
        permission: MintPermission,
        old: MintPermissionStatus,
        new: MintPermissionStatus
    ): MintPermissionAction? = when {
        !old.isGranted() && new.isGranted() -> {
            MintPermissionAction.Granted(permission)
        }
        !old.isNeedsRationale() && new.isNeedsRationale() -> {
            MintPermissionAction.NeedsRationale(permission)
        }
        !old.isDenied() && new.isDenied() -> {
            MintPermissionAction.DeniedPermanently(permission)
        }
        else -> null
    }

}