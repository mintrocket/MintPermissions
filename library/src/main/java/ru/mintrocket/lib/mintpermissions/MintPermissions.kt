package ru.mintrocket.lib.mintpermissions

import android.app.Application
import ru.mintrocket.lib.mintpermissions.internal.MintPermissionsZygote

object MintPermissions {

    val controller: MintPermissionsController by lazy {
        MintPermissionsZygote.controller
    }

    fun init(application: Application, autoInitManagers: Boolean) {
        MintPermissionsZygote.init(application, autoInitManagers)
    }

    fun createManager(): MintPermissionsManager {
        return MintPermissionsZygote.createManager()
    }
}