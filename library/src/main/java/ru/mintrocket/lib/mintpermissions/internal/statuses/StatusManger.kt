package ru.mintrocket.lib.mintpermissions.internal.statuses

import android.os.Build
import androidx.activity.ComponentActivity

internal class StatusManger(
    private val statusUpdater: StatusUpdater
) {

    fun init(activity: ComponentActivity) {
        val activeListener = { isActive: Boolean ->
            if (isActive) {
                statusUpdater.updateStatuses(activity)
            } else {
                statusUpdater.resetStatuses(activity)
            }
        }
        val observer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StatusUpdaterLifecycleObserverV16(activity, activeListener)
        } else {
            StatusUpdaterLifecycleObserverV15(activeListener)
        }
        activity.lifecycle.addObserver(observer)
    }
}