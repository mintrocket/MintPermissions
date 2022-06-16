package ru.mintrocket.lib.mintpermissions.internal

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.MintPermissionsManager
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsManager
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsQueueManager
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusManger

internal class MintPermissionsManagerImpl(
    private val queueManager: RequestsQueueManager,
    private val statusManger: StatusManger,
    private val requestsManager: RequestsManager
) : MintPermissionsManager {

    private var initCalled = false

    override fun init(activity: ComponentActivity) {
        require(!initCalled) {
            "Manager should only be initialized once per activity"
        }
        initCalled = true
        queueManager.init(activity)
        statusManger.init(activity)
        requestsManager.init(activity)
    }
}