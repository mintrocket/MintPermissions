package ru.mintrocket.lib.mintpermissions.tools.initializer

import android.app.Application

object ManagerAutoInitializer {

    private val lifecycleListener by lazy {
        InitializerLifecycleListener()
    }

    fun init(application: Application) {
        application.unregisterActivityLifecycleCallbacks(lifecycleListener)
        application.registerActivityLifecycleCallbacks(lifecycleListener)
    }

    fun addInitializer(initializer: ManagerInitializer) {
        lifecycleListener.addManagerInitializer(initializer)
    }

    fun removeInitializer(initializer: ManagerInitializer) {
        lifecycleListener.removeManagerInitializer(initializer)
    }
}