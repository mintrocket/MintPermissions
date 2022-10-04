package ru.mintrocket.lib.mintpermissions.internal

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.MintPermissionsManager
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer

internal class MintPermissionsManagerImpl(
    private val requestManager: ManagerInitializer,
    private val statusManager: ManagerInitializer,
) : MintPermissionsManager {

    @Volatile
    private var initCalled = false

    override fun init(activity: ComponentActivity) {
        require(!initCalled) {
            "Manager should only be initialized once per activity"
        }
        initCalled = true
        statusManager.init(activity)
        requestManager.init(activity)
    }
}