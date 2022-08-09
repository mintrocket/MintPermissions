package ru.mintrocket.lib.mintpermissions.ext

import android.app.Application
import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.MintPermissions
import ru.mintrocket.lib.mintpermissions.MintPermissionsConfig

/**
 * Should be initialized once on create application
 *
 * @receiver instance of your [Application]
 * @param config config for internal components
 */
public fun Application.initMintPermissions(config: MintPermissionsConfig? = null) {
    MintPermissions.init(this, config)
}

/**
 * Init manager and internal components.
 * Should be initialized once per activity
 *
 * @receiver instance of [ComponentActivity]
 * @throws Exception then initialized multiple times
 */
public fun ComponentActivity.initMintPermissionsManager() {
    MintPermissions.createManager().init(this)
}