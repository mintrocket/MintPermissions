package ru.mintrocket.lib.mintpermissions.internal

import android.app.Application
import ru.mintrocket.lib.mintpermissions.MintPermissionsConfig
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.MintPermissionsManager
import ru.mintrocket.lib.mintpermissions.ext.initMintPermissionsManager
import ru.mintrocket.lib.mintpermissions.internal.requests.PermissionsRequestConsumer
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusManger
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusProvider
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusUpdater
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusesControllerImpl
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerAutoInitializer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestZygote

internal object MintPermissionsZygote {

    private val consumer by lazy {
        PermissionsRequestConsumer(statusProvider)
    }

    private val requestsZygote by lazy {
        UiRequestZygote(consumer)
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

    private val permissionsControllerImpl by lazy {
        MintPermissionsControllerImpl(requestsZygote.controller, statusesController)
    }

    private fun createStatusManger(): StatusManger {
        return StatusManger(statusUpdater)
    }

    val controller: MintPermissionsController by lazy { permissionsControllerImpl }

    fun init(application: Application, config: MintPermissionsConfig) {
        ManagerAutoInitializer.init(application)
        if (config.autoInitManagers) {
            ManagerAutoInitializer.addInitializer {
                it.initMintPermissionsManager()
            }
        }
    }

    fun createManager(): MintPermissionsManager {
        val queueManager = requestsZygote.createManager()
        val statusManger = createStatusManger()
        return MintPermissionsManagerImpl(queueManager, statusManger)
    }
}