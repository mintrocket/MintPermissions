package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiResult
import java.util.*

internal class UiRequestControllerImpl<T, R>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : UiRequestController<T, R> {

    private val scope by lazy {
        CoroutineScope(dispatcher + SupervisorJob())
    }

    private val queueNew = FlowQueue<UiRequest<T>>()
    private val cancelFlow = MutableSharedFlow<UiRequest<T>>()
    private val resultFlow = MutableSharedFlow<UiResult<T, R>>()

    fun observeNewRequest(): Flow<UiRequest<T>> {
        return queueNew.headFlow.filterNotNull()
    }

    fun observeCancelRequest(): Flow<UiRequest<T>> {
        return cancelFlow.asSharedFlow()
    }

    fun consumeRequest(request: UiRequest<T>) {
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