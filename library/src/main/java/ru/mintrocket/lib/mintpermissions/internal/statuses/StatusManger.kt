package ru.mintrocket.lib.mintpermissions.internal.statuses

import androidx.activity.ComponentActivity

internal class StatusManger(
    private val statusUpdater: StatusUpdater
) {

    fun init(activity: ComponentActivity) {
        activity.lifecycle.addObserver(StatusUpdaterLifecycleObserver(activity) {
            statusUpdater.updateStatuses(activity)
        })
    }
}