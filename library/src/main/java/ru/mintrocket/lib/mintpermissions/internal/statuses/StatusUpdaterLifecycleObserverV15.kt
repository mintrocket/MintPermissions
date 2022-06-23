package ru.mintrocket.lib.mintpermissions.internal.statuses

import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

internal class StatusUpdaterLifecycleObserverV15(
    private val activeListener: (Boolean) -> Unit
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        activeListener.invoke(true)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        activeListener.invoke(true)
    }
}