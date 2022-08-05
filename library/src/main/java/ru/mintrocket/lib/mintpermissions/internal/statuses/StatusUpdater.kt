package ru.mintrocket.lib.mintpermissions.internal.statuses

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

internal class StatusUpdater(
    private val statusesController: StatusesController,
    private val statusProvider: StatusProvider
) {

    fun updateStatuses(activity: ComponentActivity) {
        activity.lifecycleScope.launch {
            val statuses = statusProvider.getAllStatuses(activity)
            statusesController.updateStatuses(statuses)
        }
    }

    fun resetStatuses() {
        statusesController.reset()
    }
}