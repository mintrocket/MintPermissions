package ru.mintrocket.lib.mintpermissions

import android.app.Application
import ru.mintrocket.lib.mintpermissions.internal.MintPermissionsZygote

public object MintPermissions {

    /**
     * Always return same instance.
     * You can use it everywhere, activities, viewmodels, repos, etc.
     *
     * @return single instance of [MintPermissionsController]
     */
    public val controller: MintPermissionsController by lazy {
        MintPermissionsZygote.controller
    }

    /**
     * Should be initialized once on create application
     *
     * @param application instance of your [Application]
     * @param config config for internal components
     */
    public fun init(application: Application, config: MintPermissionsConfig? = null) {
        MintPermissionsZygote.init(application, config ?: MintPermissionsConfig())
    }

    /**
     * Always return new instance.
     * Should be created and initialized in every [androidx.activity.ComponentActivity].
     *
     * @return new instance of [MintPermissionsManager]
     */
    public fun createManager(): MintPermissionsManager {
        return MintPermissionsZygote.createManager()
    }
}