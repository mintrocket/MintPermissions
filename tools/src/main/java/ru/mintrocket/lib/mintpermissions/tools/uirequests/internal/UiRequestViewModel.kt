package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import android.os.Parcelable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiResult

internal class UiRequestViewModel<T : Parcelable, R>(
    private val controller: UiRequestControllerImpl<T, R>
) {

    private val viewModelScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    private val queue by lazy { FlowQueue<UiRequest<T>>() }
    private var observingNewJob: Job? = null
    private var observingCancelJob: Job? = null

    val requestFlow = queue.headFlow

    fun finishRequest(result: UiResult<T, R>) {
        viewModelScope.launch {
            queue.remove(result.request)
            controller.sendResult(result)
        }
    }

    fun getRequestQueue(): List<UiRequest<T>> {
        return queue.queueFlow.value
    }

    fun restoreRequestQueue(requests: List<UiRequest<T>>) {
        queue.restore(requests)
    }

    fun setEnabled(enabled: Boolean) {
        if (enabled) {
            startObserving()
        } else {
            stopObserving()
        }
    }

    fun onDestroy() {
        viewModelScope.cancel()
    }

    private fun startObserving() {
        stopObserving()
        observingNewJob = controller
            .observeNewRequest()
            .onEach(queue::add)
            .onEach(controller::consumeNewRequest)
            .launchIn(viewModelScope)
        observingCancelJob = controller
            .observeCancelRequest()
            .flatMapConcat { it.asFlow() }
            .filter { queue.contains(it) }
            .onEach { queue.remove(it) }
            .onEach { controller.consumeCancelRequest(it) }
            .launchIn(viewModelScope)
    }

    private fun stopObserving() {
        observingNewJob?.cancel()
        observingNewJob = null
        observingCancelJob?.cancel()
        observingCancelJob = null
    }
}