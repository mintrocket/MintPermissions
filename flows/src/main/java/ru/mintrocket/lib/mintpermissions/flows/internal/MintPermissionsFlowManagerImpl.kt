package ru.mintrocket.lib.mintpermissions.flows.internal

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsFlowManager
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer

internal class MintPermissionsFlowManagerImpl(
    private val dialogsManager: ManagerInitializer,
    private val settingsManager: ManagerInitializer,
) : MintPermissionsFlowManager {

    private var initCalled = false

    override fun init(activity: ComponentActivity) {
        require(!initCalled) {
            "Manager should only be initialized once per activity"
        }
        initCalled = true
        dialogsManager.init(activity)
        settingsManager.init(activity)
    }
}