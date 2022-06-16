package ru.mintrocket.lib.mintpermissions.internal.requests

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.internal.models.Request
import ru.mintrocket.lib.mintpermissions.internal.models.RequestResult

internal class RequestsControllerImpl(
    private val scope: CoroutineScope
) : RequestsController {

    private val queueNew = RequestsQueue()
    private val cancelFlow = MutableSharedFlow<Request>()
    private val resultFlow = MutableSharedFlow<RequestResult>()

    override fun observeNewRequest(): Flow<Request> {
        return queueNew.headFlow.filterNotNull()
    }

    override fun observeCancelRequest(): Flow<Request> {
        return cancelFlow.asSharedFlow()
    }

    override suspend fun consumeRequest(request: Request) {
        queueNew.remove(request)
    }

    override suspend fun sendResult(result: RequestResult) {
        resultFlow.emit(result)
    }

    override suspend fun request(request: Request): RequestResult {
        return resultFlow
            .onStart { startRequest(request) }
            .onCompletion { completeRequest(request) }
            .first { it.request == request }
    }

    private fun startRequest(request: Request) {
        scope.launch {
            queueNew.add(request)
        }
    }

    private fun completeRequest(request: Request) {
        scope.launch {
            queueNew.remove(request)
            cancelFlow.emit(request)
        }
    }
}