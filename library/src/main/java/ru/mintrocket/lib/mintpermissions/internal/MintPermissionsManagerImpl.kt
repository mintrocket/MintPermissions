package ru.mintrocket.lib.mintpermissions.internal

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.MintPermissionsManager
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsController
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsManager
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsQueueManager
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusManger
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusProvider
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusUpdater

internal class MintPermissionsManagerImpl(
    private val requestsController: RequestsController,
    private val statusProvider: StatusProvider,
    private val statusUpdater: StatusUpdater,
) : MintPermissionsManager {

    private var initCalled = false

    private val queueManager by lazy {
        RequestsQueueManager(requestsController)
    }

    private val statusManger by lazy {
        StatusManger(statusUpdater)
    }

    private val requestsManager by lazy {
        RequestsManager(queueManager, statusUpdater, statusProvider, requestsController)
    }

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