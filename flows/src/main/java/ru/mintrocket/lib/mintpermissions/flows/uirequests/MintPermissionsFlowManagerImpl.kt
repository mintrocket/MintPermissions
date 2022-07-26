package ru.mintrocket.lib.mintpermissions.flows.uirequests

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer

class MintPermissionsFlowManagerImpl(
    private val dialogsManager: ManagerInitializer,
    private val settingsManager: ManagerInitializer,
) : MintPermissionsFlowManager {

    override fun init(activity: ComponentActivity) {
        dialogsManager.init(activity)
        settingsManager.init(activity)
    }
}