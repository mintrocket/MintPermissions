package ru.mintrocket.lib.mintpermissions

import android.app.Application
import ru.mintrocket.lib.mintpermissions.internal.MintPermissionsZygote

object MintPermissions {

    val controller: MintPermissionsController by lazy {
        MintPermissionsZygote.controller
    }

    fun init(application: Application, config: MintPermissionsConfig? = null) {
        MintPermissionsZygote.init(application, config ?: MintPermissionsConfig())
    }

    fun createManager(): MintPermissionsManager {
        return MintPermissionsZygote.createManager()
    }
}