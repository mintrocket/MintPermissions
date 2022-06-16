package ru.mintrocket.lib.mintpermissions.ext

import android.app.Application
import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.MintPermissions
import ru.mintrocket.lib.mintpermissions.MintPermissionsConfig

fun Application.initMintPermissions(config: MintPermissionsConfig? = null) {
    MintPermissions.init(this, config)
}

fun ComponentActivity.initMintPermissionsManager() {
    MintPermissions.createManager().init(this)
}