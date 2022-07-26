package ru.mintrocket.lib.mintpermissions.internal.requests

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mintrocket.lib.mintpermissions.ext.isDenied
import ru.mintrocket.lib.mintpermissions.ext.isGranted
import ru.mintrocket.lib.mintpermissions.ext.isNeedsRationale
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusProvider
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionAction
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest
import kotlin.coroutines.resume

internal class PermissionsRequestConsumer(
    private val statusProvider: StatusProvider,
) : UiRequestConsumer<List<MintPermission>, List<MintPermissionResult>> {

    override suspend fun request(
        activity: ComponentActivity,
        request: UiRequest<List<MintPermission>>
    ): List<MintPermissionResult> = suspendCancellableCoroutine { continuation ->
        val resultRegistry = activity.activityResultRegistry
        val contract = ActivityResultContracts.RequestMultiplePermissions()
        val oldMap = statusProvider
            .getStatuses(activity, request.data)
            .associateBy { it.permission }

        val launcher = resultRegistry.register(request.key, contract) { resultMap ->
            if (!continuation.isActive || (request.data.isNotEmpty() && resultMap.isEmpty())) {
                return@register
            }
            val statuses = statusProvider.getStatuses(activity, request.data)
            val newMap = statuses.associateBy { it.permission }

            val results = statuses.map { status ->
                val permission = status.permission
                val old = oldMap.getValue(permission)
                val new = newMap.getValue(permission)
                val action = computeAction(permission, old, new)
                MintPermissionResult(status, action)
            }
            continuation.resume(results)
        }

        launcher.launch(request.data.toTypedArray())
        continuation.invokeOnCancellation {
            launcher.unregister()
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