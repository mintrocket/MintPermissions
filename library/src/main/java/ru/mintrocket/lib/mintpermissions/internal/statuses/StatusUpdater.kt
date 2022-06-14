package ru.mintrocket.lib.mintpermissions.internal.statuses

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class StatusUpdater(
    private val statusesController: StatusesController,
    private val statusProvider: StatusProvider
) {

    fun init(activity: ComponentActivity) {
        activity.lifecycle.addObserver(StatusUpdaterLifecycleObserver(activity) {
            updateStatuses(activity)
        })
    }

    fun updateStatuses(activity: ComponentActivity) {
        activity.lifecycleScope.launch(Dispatchers.Default) {
            statusesController.updateStatuses(statusProvider.getAllStatuses(activity))
        }
    }
}