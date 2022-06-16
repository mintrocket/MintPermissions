package ru.mintrocket.lib.mintpermissions

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.mintrocket.lib.mintpermissions.internal.MintPermissionsActivityLifecycleListener
import ru.mintrocket.lib.mintpermissions.internal.MintPermissionsControllerImpl
import ru.mintrocket.lib.mintpermissions.internal.MintPermissionsManagerImpl
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsControllerImpl
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusProvider
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusUpdater
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusesControllerImpl

object MintPermissionsInitializer {

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

    val permissionsController: MintPermissionsController by lazy { permissionsControllerImpl }

    fun init(application: Application) {
        application.unregisterActivityLifecycleCallbacks(lifecycleListener)
        application.registerActivityLifecycleCallbacks(lifecycleListener)
    }

    fun createManager(): MintPermissionsManager {
        return MintPermissionsManagerImpl(requestsController, statusProvider, statusUpdater)
    }
}