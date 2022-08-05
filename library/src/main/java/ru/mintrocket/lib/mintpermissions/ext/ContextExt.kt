package ru.mintrocket.lib.mintpermissions.ext

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import ru.mintrocket.lib.mintpermissions.MintPermissions
import ru.mintrocket.lib.mintpermissions.MintPermissionsConfig

/**
 * Should be initialized once on create application
 *
 * @receiver instance of your [Application]
 * @param config config for internal components
 */
fun Application.initMintPermissions(config: MintPermissionsConfig? = null) {
    MintPermissions.init(this, config)
}

/**
 * Init manager and internal components.
 * Should be initialized once per activity
 *
 * @receiver instance of [ComponentActivity]
 * @throws Exception then initialized multiple times
 */
fun ComponentActivity.initMintPermissionsManager() {
    MintPermissions.createManager().init(this)
}