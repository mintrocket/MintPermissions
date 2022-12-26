package ru.mintrocket.lib.mintpermissions.internal.statuses

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer

internal class StatusManager(
    private val statusUpdater: StatusUpdater
) : ManagerInitializer {

    override fun init(activity: ComponentActivity) {
        val observer = StatusUpdaterLifecycleObserver(activity) { isActive: Boolean ->
            if (isActive) {
                statusUpdater.updateStatuses(activity)
            } else {
                statusUpdater.resetStatuses()
            }
        }
        activity.lifecycle.addObserver(observer)
    }
}