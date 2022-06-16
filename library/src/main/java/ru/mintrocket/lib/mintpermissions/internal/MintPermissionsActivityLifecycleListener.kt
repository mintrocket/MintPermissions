package ru.mintrocket.lib.mintpermissions.internal

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusUpdater

internal class MintPermissionsActivityLifecycleListener(
    private val statusUpdater: StatusUpdater
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        (activity as? ComponentActivity)?.also {
            statusUpdater.updateStatuses(it)
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