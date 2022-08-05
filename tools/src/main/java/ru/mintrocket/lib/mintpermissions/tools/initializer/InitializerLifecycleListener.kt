package ru.mintrocket.lib.mintpermissions.tools.initializer

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity

internal class InitializerLifecycleListener : Application.ActivityLifecycleCallbacks {

    private val managerInitializers = mutableListOf<ManagerInitializer>()

    fun addManagerInitializer(initializer: ManagerInitializer) {
        managerInitializers.add(initializer)
    }

    fun removeManagerInitializer(initializer: ManagerInitializer) {
        managerInitializers.remove(initializer)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        (activity as? ComponentActivity)?.also {
            managerInitializers.toList().forEach { initializer ->
                initializer.init(activity)
            }
        }
    }

    override fun onActivityStarted(activity: Activity) {
        // Do nothing.
    }

    override fun onActivityResumed(activity: Activity) {
        // Do nothing.
    }

    override fun onActivityPaused(activity: Activity) {
        // Do nothing.
    }

    override fun onActivityStopped(activity: Activity) {
        // Do nothing.
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // Do nothing.
    }

    override fun onActivityDestroyed(activity: Activity) {
        // Do nothing.
    }
}