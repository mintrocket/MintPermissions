package ru.mintrocket.lib.mintpermissions.internal.statuses

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

class StatusProvider {

    private var cachedPackagePermissions: List<MintPermission>? = null

    fun getAllStatuses(activity: ComponentActivity): List<MintPermissionStatus> {
        return getStatuses(activity, getPackagePermissions(activity))
    }

    fun getStatuses(
        activity: ComponentActivity,
        permissions: List<MintPermission>
    ): List<MintPermissionStatus> = permissions.map { permission ->
        val isGranted = ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
        toStatus(permission, isGranted, activity)
    }

    private fun ComponentActivity.shouldShowRationale(permission: MintPermission): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(permission)
        } else {
            false
        }
    }

    private fun toStatus(
        permission: MintPermission,
        isGranted: Boolean,
        activity: ComponentActivity
    ): MintPermissionStatus {
        return if (isGranted) {
            MintPermissionStatus.Granted(permission)
        } else {
            val needsRationale = activity.shouldShowRationale(permission)
            if (needsRationale) {
                MintPermissionStatus.NeedsRationale(permission)
            } else {
                val hasPermission = getPackagePermissions(activity).contains(permission)
                if (hasPermission) {
                    MintPermissionStatus.Denied(permission)
                } else {
                    MintPermissionStatus.NotFound(permission)
                }
            }
        }
    }

    private fun getPackagePermissions(activity: ComponentActivity): List<MintPermission> {
        if (cachedPackagePermissions == null) {
            val info = activity.packageManager.getPackageInfo(
                activity.packageName,
                PackageManager.GET_PERMISSIONS
            )
            cachedPackagePermissions = info.requestedPermissions?.toList().orEmpty()
        }
        return requireNotNull(cachedPackagePermissions) {
            "cachedPackagePermissions is null after init"
        }
    }
}