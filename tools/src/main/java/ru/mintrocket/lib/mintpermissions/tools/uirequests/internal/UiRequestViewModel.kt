package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConfig
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiResult

internal class UiRequestViewModel<T, R>(
    private val handle: SavedStateHandle,
    private val config: UiRequestConfig,
    private val controller: UiRequestControllerImpl<T, R>
) : ViewModel() {

    companion object {
        private const val KEY_REQUESTS = "requests"
    }

    private val queue by lazy { FlowQueue<UiRequest<T>>() }
    private var observingNewJob: Job? = null

    val requestFlow = queue.headFlow

    init {
        if (config.saveQueueState) {
            val savedRequests = handle.get<List<UiRequest<T>>>(KEY_REQUESTS).orEmpty()
            queue.restore(savedRequests)
            queue.queueFlow
                .onEach { handle[KEY_REQUESTS] = it }
                .launchIn(viewModelScope)
        }

        controller
            .observeCancelRequest()
            .filter { queue.contains(it) }
            .onEach { queue.remove(it) }
            .launchIn(viewModelScope)
    }

    fun finishRequest(result: UiResult<T, R>) {
        viewModelScope.launch {
            queue.remove(result.request)
            controller.sendResult(result)
        }
    }

    fun setEnabled(enabled: Boolean) {
        if (enabled) {
            startObservingNew()
        } else {
            stopObservingNew()
        }
    }

    private fun startObservingNew() {
        stopObservingNew()
        observingNewJob = controller
            .observeNewRequest()
            .onEach(queue::add)
            .onEach(controller::consumeRequest)
            .launchIn(viewModelScope)
    }

    private fun stopObservingNew() {
        observingNewJob?.cancel()
        observingNewJob = null
    }
}