package ru.mintrocket.lib.mintpermissions.internal.statuses

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class StatusUpdater(
    private val statusesController: StatusesController,
    private val statusProvider: StatusProvider
) {

    fun updateStatuses(
        activity: ComponentActivity,
        context: CoroutineContext = Dispatchers.Default
    ) {
        activity.lifecycleScope.launch(context) {
            statusesController.updateStatuses(statusProvider.getAllStatuses(activity))
        }
    }

    fun resetStatuses(
        activity: ComponentActivity,
        context: CoroutineContext = Dispatchers.Default
    ) {
        activity.lifecycleScope.launch(context) {
            statusesController.reset()
        }
    }
}