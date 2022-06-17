package ru.mintrocket.lib.mintpermissions.internal.statuses

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

internal class StatusUpdaterLifecycleObserverV15(
    private val updateListener: () -> Unit
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        updateListener.invoke()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        updateListener.invoke()
    }
}