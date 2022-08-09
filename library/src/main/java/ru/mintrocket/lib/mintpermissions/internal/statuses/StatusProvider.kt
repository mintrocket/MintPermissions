package ru.mintrocket.lib.mintpermissions.internal.statuses

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mintrocket.lib.mintpermissions.internal.ext.getPackagePermissions
import ru.mintrocket.lib.mintpermissions.internal.ext.isPermissionGranted
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

internal class StatusProvider(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    private var cachedPackagePermissions: List<MintPermission>? = null

    suspend fun getAllStatuses(activity: ComponentActivity): List<MintPermissionStatus> {
        return withContext(dispatcher) {
            getStatuses(activity, getCachedPackagePermissions(activity))
        }
    }

    suspend fun getStatuses(
        activity: ComponentActivity,
        permissions: List<MintPermission>
    ): List<MintPermissionStatus> {
        return withContext(dispatcher) {
            permissions.map { permission ->
                val isGranted = activity.isPermissionGranted(permission)
                toStatus(permission, isGranted, activity)
            }
        }
    }

    private fun toStatus(
        permission: MintPermission,
        isGranted: Boolean,
        activity: ComponentActivity
    ) = when {
        isGranted -> MintPermissionStatus.Granted(permission)
        ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
            MintPermissionStatus.NeedsRationale(permission)
        }
        getCachedPackagePermissions(activity).contains(permission) -> {
            MintPermissionStatus.Denied(permission)
        }
        else -> MintPermissionStatus.NotFound(permission)
    }

    private fun getCachedPackagePermissions(context: Context): List<MintPermission> {
        val permissions = cachedPackagePermissions ?: context.getPackagePermissions()
        cachedPackagePermissions = permissions
        return permissions
    }
}