package ru.mintrocket.mintpermissions.common

import android.content.Context
import android.widget.Toast

object PermissionEvents {

    fun grantedToast(context: Context) {
        Toast.makeText(context, "This is camera, trust me", Toast.LENGTH_SHORT).show()
    }
}