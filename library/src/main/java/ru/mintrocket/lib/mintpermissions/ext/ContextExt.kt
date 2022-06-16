package ru.mintrocket.lib.mintpermissions.ext

import android.app.Application
import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.MintPermissions

fun Application.initMintPermissions(autoInitManagers: Boolean) {
    MintPermissions.init(this, autoInitManagers)
}

fun ComponentActivity.initMintPermissionsManager() {
    MintPermissions.createManager().init(this)
}