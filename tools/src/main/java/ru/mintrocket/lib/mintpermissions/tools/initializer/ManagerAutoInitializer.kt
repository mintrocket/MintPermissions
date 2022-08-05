package ru.mintrocket.lib.mintpermissions.tools.initializer

import android.app.Application

public object ManagerAutoInitializer {

    private val lifecycleListener by lazy {
        InitializerLifecycleListener()
    }

    public fun init(application: Application) {
        // It is necessary so that several libraries can use automatic initialization
        application.unregisterActivityLifecycleCallbacks(lifecycleListener)
        application.registerActivityLifecycleCallbacks(lifecycleListener)
    }

    public fun addInitializer(initializer: ManagerInitializer) {
        lifecycleListener.addManagerInitializer(initializer)
    }

    public fun removeInitializer(initializer: ManagerInitializer) {
        lifecycleListener.removeManagerInitializer(initializer)
    }
}