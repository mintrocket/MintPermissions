package ru.mintrocket.lib.mintpermissions

import ru.mintrocket.lib.mintpermissions.internal.MintPermissionsControllerImpl
import ru.mintrocket.lib.mintpermissions.internal.MintPermissionsManagerImpl
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsControllerImpl
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusesControllerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object MintPermissionsInitializer {

    private val coroutineScope by lazy {
        CoroutineScope(Dispatchers.Default)
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

    val permissionsController: MintPermissionsController by lazy { permissionsControllerImpl }

    fun createManager(): MintPermissionsManager {
        return MintPermissionsManagerImpl(statusesController, requestsController)
    }
}