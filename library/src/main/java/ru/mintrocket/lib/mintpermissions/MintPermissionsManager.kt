package ru.mintrocket.lib.mintpermissions

import androidx.activity.ComponentActivity

/**
 * Mint permissions manager
 * This manager is needed to connect the activity and the controller
 *
 * @constructor Create empty Mint permissions manager
 */
interface MintPermissionsManager {

    /**
     * Init manager and internal components.
     * Should be initialized once per activity
     *
     * @param activity instance of [ComponentActivity]
     * @throws Exception then initialized multiple times
     */
    fun init(activity: ComponentActivity)
}