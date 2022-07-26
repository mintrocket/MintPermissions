package ru.mintrocket.lib.mintpermissions.flows.internal

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsFlowManager
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer

internal class MintPermissionsFlowManagerImpl(
    private val dialogsManager: ManagerInitializer,
    private val settingsManager: ManagerInitializer,
) : MintPermissionsFlowManager {

    override fun init(activity: ComponentActivity) {
        dialogsManager.init(activity)
        settingsManager.init(activity)
    }
}