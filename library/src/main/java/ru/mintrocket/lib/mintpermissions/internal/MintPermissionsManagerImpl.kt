package ru.mintrocket.lib.mintpermissions.internal

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.MintPermissionsManager
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusManger
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer

internal class MintPermissionsManagerImpl(
    private val requestManager: ManagerInitializer,
    private val statusManger: ManagerInitializer,
) : MintPermissionsManager {

    private var initCalled = false

    override fun init(activity: ComponentActivity) {
        require(!initCalled) {
            "Manager should only be initialized once per activity"
        }
        initCalled = true
        statusManger.init(activity)
        requestManager.init(activity)
    }
}