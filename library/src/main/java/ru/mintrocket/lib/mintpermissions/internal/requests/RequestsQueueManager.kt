package ru.mintrocket.lib.mintpermissions.internal.requests

import androidx.activity.ComponentActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ru.mintrocket.lib.mintpermissions.internal.models.Request
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class RequestsQueueManager(
    private val requestsController: RequestsController
) {

    companion object {
        private const val SAVED_STATE_KEY = "mintpermissions_state"
        private const val REQUESTS_KEY = "requests"
    }

    private val queue by lazy { RequestsQueue() }

    val requestFlow = queue.headFlow.filterNotNull()

    fun init(activity: ComponentActivity) {
        restoreSavedState(activity)
        registerSavedStateProvider(activity)

        activity.lifecycleScope.launch {
            activity.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                requestsController
                    .observeNewRequest()
                    .onEach {
                        queue.add(it)
                        requestsController.consumeRequest(it)
                    }
                    .launchIn(this)
            }
        }

        requestsController
            .observeCancelRequest()
            .filter { queue.contains(it) }
            .onEach { queue.remove(it) }
            .launchIn(activity.lifecycleScope)
    }

    fun finishRequest(request: Request) {
        queue.remove(request)
    }

    private fun registerSavedStateProvider(activity: ComponentActivity) {
        activity.savedStateRegistry.registerSavedStateProvider(SAVED_STATE_KEY) {
            bundleOf(REQUESTS_KEY to ArrayList(queue.getAll()))
        }
    }

    private fun restoreSavedState(activity: ComponentActivity) {
        val savedRequests = activity.savedStateRegistry
            .consumeRestoredStateForKey(SAVED_STATE_KEY)
            ?.getParcelableArrayList<Request>(REQUESTS_KEY)
            .orEmpty()

        queue.restore(savedRequests)
    }
}