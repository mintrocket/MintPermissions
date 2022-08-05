package ru.mintrocket.lib.mintpermissions.internal.statuses

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.core.os.BuildCompat
import androidx.core.view.KeyEventDispatcher.Component
import ru.mintrocket.lib.mintpermissions.ext.checkSelfPermissionIsGranted
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
        val isGranted = activity.checkSelfPermissionIsGranted(permission)
        toStatus(permission, isGranted, activity)
    }

    private fun ComponentActivity.shouldShowRationale(permission: MintPermission): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                shouldShowRequestPermissionRationale(permission)
    }

    private fun toStatus(
        permission: MintPermission,
        isGranted: Boolean,
        activity: ComponentActivity
    ) = when {
        isGranted -> MintPermissionStatus.Granted(permission)
        activity.shouldShowRationale(permission) -> {
            MintPermissionStatus.NeedsRationale(permission)
        }
        permission in getPackagePermissions(activity) -> {
            MintPermissionStatus.Denied(permission)
        }
        else -> MintPermissionStatus.NotFound(permission)
    }

    private fun getPackagePermissions(activity: ComponentActivity): List<MintPermission> {
        val cachedPackagePermissions = cachedPackagePermissions
        if (cachedPackagePermissions == null) {
            val info = activity.packageManager
                .getPackageInfo(activity.packageName, PackageManager.GET_PERMISSIONS)
            return info.requestedPermissions?.toList().orEmpty().also { permissions ->
                this.cachedPackagePermissions = permissions
            }
        }

        return cachedPackagePermissions
    }
}