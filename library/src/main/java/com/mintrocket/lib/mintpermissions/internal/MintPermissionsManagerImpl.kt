package com.mintrocket.lib.mintpermissions.internal

import androidx.activity.ComponentActivity
import com.mintrocket.lib.mintpermissions.MintPermissionsManager
import com.mintrocket.lib.mintpermissions.internal.requests.RequestsController
import com.mintrocket.lib.mintpermissions.internal.requests.RequestsLauncher
import com.mintrocket.lib.mintpermissions.internal.requests.RequestsQueueManager
import com.mintrocket.lib.mintpermissions.internal.statuses.StatusProvider
import com.mintrocket.lib.mintpermissions.internal.statuses.StatusUpdater
import com.mintrocket.lib.mintpermissions.internal.statuses.StatusesController

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