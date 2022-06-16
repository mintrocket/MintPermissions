package ru.mintrocket.mintpermissions.common

import android.content.Context
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.mintrocket.lib.mintpermissions.models.MintPermission

object PermissionEvents {

    fun grantedToast(context: Context) {
        Toast.makeText(context, "This is camera, trust me", Toast.LENGTH_SHORT).show()
    }

    fun deniedDialog(context: Context, permissions: List<MintPermission>) {
        MaterialAlertDialogBuilder(context)
            .setMessage("Denied: $permissions")
            .setPositiveButton("Open settings") { _, _ ->
                Routes.appSettings(context)
            }
            .setNeutralButton("Not now", null)
            .show()
    }

    fun rationaleDialog(
        context: Context,
        permissions: List<MintPermission>,
        onPositive: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setMessage("Rationale: $permissions")
            .setPositiveButton("Request again") { _, _ ->
                onPositive.invoke()
            }
            .setNeutralButton("Not now", null)
            .show()
    }

    fun deniedPermanentlyDialog(context: Context, permissions: List<MintPermission>) {
        MaterialAlertDialogBuilder(context)
            .setMessage("Denied Permanently: $permissions")
            .setPositiveButton("Open settings") { _, _ ->
                Routes.appSettings(context)
            }
            .setNeutralButton("Not now", null)
            .show()
    }
}