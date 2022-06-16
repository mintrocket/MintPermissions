package ru.mintrocket.lib.mintpermissions.internal.requests

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mintrocket.lib.mintpermissions.ext.isDenied
import ru.mintrocket.lib.mintpermissions.ext.isGranted
import ru.mintrocket.lib.mintpermissions.ext.isNeedsRationale
import ru.mintrocket.lib.mintpermissions.internal.models.Request
import ru.mintrocket.lib.mintpermissions.internal.models.RequestResult
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusProvider
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusUpdater
import ru.mintrocket.lib.mintpermissions.models.*
import kotlin.coroutines.resume

internal class RequestsManager(
    private val queueManager: RequestsQueueManager,
    private val statusUpdater: StatusUpdater,
    private val statusProvider: StatusProvider,
    private val requestsController: RequestsController
) {

    fun init(activity: ComponentActivity) {
        queueManager
            .requestFlow
            .map { request(activity, it) }
            .onEach {
                queueManager.finishRequest(it.request)
                requestsController.sendResult(it)
                statusUpdater.updateStatuses(activity)
            }
            .launchIn(activity.lifecycleScope)
    }

    private suspend fun request(
        activity: ComponentActivity,
        request: Request
    ): RequestResult = suspendCancellableCoroutine { continuation ->
        val resultRegistry = activity.activityResultRegistry
        val contract = ActivityResultContracts.RequestMultiplePermissions()
        val oldMap = statusProvider
            .getStatuses(activity, request.permissions)
            .associateBy { it.permission }

        val launcher = resultRegistry.register(request.key.toString(), contract) {
            if (!continuation.isActive || it.isEmpty()) {
                return@register
            }
            val statuses = statusProvider.getStatuses(activity, request.permissions)
            val newMap = statuses.associateBy { it.permission }

            val results = statuses.map { status ->
                val permission = status.permission
                val old = oldMap.getValue(permission)
                val new = newMap.getValue(permission)
                val action = computeAction(permission, old, new)
                MintPermissionResult(status, action)
            }
            continuation.resume(RequestResult(request, MintPermissionMultipleResult(results)))
        }

        launcher.launch(request.permissions.toTypedArray())
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