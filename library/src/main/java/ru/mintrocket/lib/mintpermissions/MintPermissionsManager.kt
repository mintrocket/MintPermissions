package ru.mintrocket.lib.mintpermissions

import androidx.activity.ComponentActivity

/**
 * Mint permissions manager
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