package ru.mintrocket.lib.mintpermissions.internal

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.mintrocket.lib.mintpermissions.MintPermissionsConfig
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.MintPermissionsManager
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsControllerImpl
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsManager
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsQueueManager
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusManger
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusProvider
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusUpdater
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusesControllerImpl

internal object MintPermissionsZygote {

    private val coroutineScope by lazy {
        CoroutineScope(Dispatchers.Default)
    }

    private val statusProvider by lazy {
        StatusProvider()
    }

    private val statusUpdater by lazy {
        StatusUpdater(statusesController, statusProvider)
    }

    private val statusesController by lazy {
        StatusesControllerImpl()
    }

    private val requestsController by lazy {
        RequestsControllerImpl(coroutineScope)
    }

    private val permissionsControllerImpl by lazy {
        MintPermissionsControllerImpl(requestsController, statusesController)
    }

    private val lifecycleListener by lazy {
        MintPermissionsActivityLifecycleListener(statusUpdater)
    }

    private fun createQueueManager(): RequestsQueueManager {
        return RequestsQueueManager(requestsController)
    }

    private fun createStatusManger(): StatusManger {
        return StatusManger(statusUpdater)
    }

    private fun createRequestsManager(queueManager: RequestsQueueManager): RequestsManager {
        return RequestsManager(queueManager, statusUpdater, statusProvider, requestsController)
    }

    val controller: MintPermissionsController by lazy { permissionsControllerImpl }

    fun init(application: Application, config: MintPermissionsConfig) {
        lifecycleListener.setAutoInitMangers(config.autoInitManagers)
        application.unregisterActivityLifecycleCallbacks(lifecycleListener)
        application.registerActivityLifecycleCallbacks(lifecycleListener)
    }

    fun createManager(): MintPermissionsManager {
        val queueManager = createQueueManager()
        val statusManger = createStatusManger()
        val requestsManager = createRequestsManager(queueManager)
        return MintPermissionsManagerImpl(queueManager, statusManger, requestsManager)
    }
}