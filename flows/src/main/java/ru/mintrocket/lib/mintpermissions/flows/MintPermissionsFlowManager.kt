package ru.mintrocket.lib.mintpermissions.flows

import androidx.activity.ComponentActivity

/**
 * Mint permissions flow manager
 * This manager is needed to connect the activity and the controller
 *
 * @constructor Create empty Mint permissions flow manager
 */
public interface MintPermissionsFlowManager {

    /**
     * Init manager and internal components.
     * Should be initialized once per activity
     *
     * @param activity instance of [ComponentActivity]
     * @throws Exception then initialized multiple times
     */
    public fun init(activity: ComponentActivity)
}