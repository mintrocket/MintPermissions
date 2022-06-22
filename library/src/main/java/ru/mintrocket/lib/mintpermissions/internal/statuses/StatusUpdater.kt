package ru.mintrocket.lib.mintpermissions.internal.statuses

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class StatusUpdater(
    private val statusesController: StatusesController,
    private val statusProvider: StatusProvider
) {

    fun updateStatuses(activity: ComponentActivity) {
        activity.lifecycleScope.launch(Dispatchers.Default) {
            statusesController.updateStatuses(statusProvider.getAllStatuses(activity))
        }
    }

    fun resetStatuses(activity: ComponentActivity) {
        activity.lifecycleScope.launch(Dispatchers.Default) {
            statusesController.reset()
        }
    }
}