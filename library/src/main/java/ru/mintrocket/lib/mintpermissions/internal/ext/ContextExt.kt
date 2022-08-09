package ru.mintrocket.lib.mintpermissions.internal.ext

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import ru.mintrocket.lib.mintpermissions.models.MintPermission

internal fun Context.isPermissionGranted(permission: MintPermission): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}

internal fun Context.getPackagePermissions(): List<MintPermission> {
    val info = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
    return info.requestedPermissions?.toList().orEmpty()
}