package ru.mintrocket.lib.mintpermissions.flows.ext

import android.app.Application
import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.MintPermissionsConfig
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsFlow

/**
 * Should be initialized once on create application
 *
 * @receiver instance of your [Application]
 * @param config config for internal components
 */
public fun Application.initMintPermissionsFlow(config: MintPermissionsConfig? = null) {
    MintPermissionsFlow.init(this, config)
}

/**
 * Init manager and internal components.
 * Should be initialized once per activity
 *
 * @receiver instance of [ComponentActivity]
 * @throws Exception then initialized multiple times
 */
public fun ComponentActivity.initMintPermissionsFlowManager() {
    MintPermissionsFlow.createManager().init(this)
}