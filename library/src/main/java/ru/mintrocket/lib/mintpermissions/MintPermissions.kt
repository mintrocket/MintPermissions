package ru.mintrocket.lib.mintpermissions

import android.app.Application
import ru.mintrocket.lib.mintpermissions.internal.MintPermissionsZygote

object MintPermissions {

    /**
     * Always return new instance.
     * You can use it everywhere, activities, viewmodels, repos, etc.
     *
     * @return single instance of [MintPermissionsController]
     */
    val controller: MintPermissionsController by lazy {
        MintPermissionsZygote.controller
    }

    /**
     * Should be initialized once on create application
     *
     * @param application instance of your [Application]
     * @param config config for internal components
     */
    fun init(application: Application, config: MintPermissionsConfig? = null) {
        MintPermissionsZygote.init(application, config ?: MintPermissionsConfig())
    }

    /**
     * Always return new instance.
     * Should be created and initialized in every [androidx.activity.ComponentActivity].
     *
     * @return new instance of [MintPermissionsManager]
     */
    fun createManager(): MintPermissionsManager {
        return MintPermissionsZygote.createManager()
    }
}