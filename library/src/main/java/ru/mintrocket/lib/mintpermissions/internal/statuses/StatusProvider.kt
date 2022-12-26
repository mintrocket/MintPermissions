package ru.mintrocket.lib.mintpermissions.internal.statuses

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mintrocket.lib.mintpermissions.BuildConfig
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
                val isGranted = safePermission {
                    activity.isPermissionGranted(permission)
                }
                toStatus(permission, isGranted, activity)
            }
        }
    }

    private fun toStatus(
        permission: MintPermission,
        isGranted: Boolean,
        activity: ComponentActivity
    ): MintPermissionStatus {
        val isNeedsRationale by lazy {
            safePermission {
                ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
            }
        }
        return when {
            isGranted -> MintPermissionStatus.Granted(permission)
            isNeedsRationale -> {
                MintPermissionStatus.NeedsRationale(permission)
            }
            getCachedPackagePermissions(activity).contains(permission) -> {
                MintPermissionStatus.Denied(permission)
            }
            else -> MintPermissionStatus.NotFound(permission)
        }
    }

    private fun getCachedPackagePermissions(context: Context): List<MintPermission> {
        val permissions = cachedPackagePermissions ?: context.getPackagePermissions()
        cachedPackagePermissions = permissions
        return permissions
    }

    // Needs only for android 23. System can throw exception if declared permission not supported
    // find "unknown permission" in
    // https://android.googlesource.com/platform/frameworks/base/+/aa2ffea8baea65c13ac2b841b3d581f28261dd2b/services/core/java/com/android/server/pm/permission/PermissionManagerService.java
    private fun safePermission(block: () -> Boolean): Boolean {
        return try {
            block()
        } catch (ex: IllegalArgumentException) {
            if (BuildConfig.DEBUG) {
                Log.e("StatusProvider", "MintPermissions caught exception, but it is ok. $ex")
            }
            false
        }
    }
}