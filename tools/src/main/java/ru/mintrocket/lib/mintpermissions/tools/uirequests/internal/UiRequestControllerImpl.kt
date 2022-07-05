package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController
import ru.mintrocket.lib.mintpermissions.tools.uirequests.model.UiRequest
import ru.mintrocket.lib.mintpermissions.tools.uirequests.model.UiResult
import java.util.*

class UiRequestControllerImpl<T, R>(private val scope: CoroutineScope) : UiRequestController<T, R> {

    private val queueNew = FlowQueue<UiRequest<T>>()
    private val cancelFlow = MutableSharedFlow<UiRequest<T>>()
    private val resultFlow = MutableSharedFlow<UiResult<T, R>>()

    fun observeNewRequest(): Flow<UiRequest<T>> {
        return queueNew.headFlow.filterNotNull()
    }

    fun observeCancelRequest(): Flow<UiRequest<T>> {
        return cancelFlow.asSharedFlow()
    }

    suspend fun consumeRequest(request: UiRequest<T>) {
        queueNew.remove(request)
    }

    suspend fun sendResult(result: UiResult<T, R>) {
        resultFlow.emit(result)
    }

    override suspend fun request(requestData: T): R {
        val request = UiRequest(UUID.randomUUID().toString(), requestData)
        return resultFlow
            .onStart { startRequest(request) }
            .onCompletion { completeRequest(request) }
            .first { it.request == request }
            .data
    }

    private fun startRequest(request: UiRequest<T>) {
        scope.launch {
            queueNew.add(request)
        }
    }

    private fun completeRequest(request: UiRequest<T>) {
        scope.launch {
            queueNew.remove(request)
            cancelFlow.emit(request)
        }
    }
}