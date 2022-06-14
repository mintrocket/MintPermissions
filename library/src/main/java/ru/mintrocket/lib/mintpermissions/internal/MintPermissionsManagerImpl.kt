package ru.mintrocket.lib.mintpermissions.internal

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.MintPermissionsManager
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsController
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsLauncher
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsQueueManager
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusProvider
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusUpdater
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusesController

internal class MintPermissionsManagerImpl(
    private val statusesController: StatusesController,
    private val requestsController: RequestsController
) : MintPermissionsManager {

    private var initCalled = false

    private val statusProvider by lazy {
        StatusProvider()
    }

    private val queueManager by lazy {
        RequestsQueueManager(requestsController)
    }

    private val statusUpdater by lazy {
        StatusUpdater(statusesController, statusProvider)
    }

    private val requestsManager by lazy {
        RequestsLauncher(queueManager, statusUpdater, statusProvider, requestsController)
    }

    override fun init(activity: ComponentActivity) {
        require(!initCalled) {
            "Manager should only be initialized once per activity"
        }
        initCalled = true
        queueManager.init(activity)
        statusUpdater.init(activity)
        requestsManager.init(activity)
    }
}