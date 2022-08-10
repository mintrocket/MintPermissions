package ru.mintrocket.lib.mintpermissions.internal.statuses

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class StatusUpdater(
    private val statusesController: StatusesController,
    private val statusProvider: StatusProvider
) {

    private var updaterJob: Job? = null

    fun updateStatuses(activity: ComponentActivity) {
        updaterJob?.cancel()
        updaterJob = activity.lifecycleScope.launch {
            val statuses = statusProvider.getAllStatuses(activity)
            statusesController.updateStatuses(statuses)
        }
    }

    fun resetStatuses() {
        updaterJob?.cancel()
        statusesController.reset()
    }
}