package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import android.os.Parcelable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiResult
import java.util.UUID

internal class UiRequestControllerImpl<T : Parcelable, R> : UiRequestController<T, R> {

    private val queueNew = FlowQueue<UiRequest<T>>()
    private val queueCancel = FlowQueue<UiRequest<T>>()
    private val resultFlow = MutableSharedFlow<UiResult<T, R>>()

    fun observeNewRequest(): Flow<UiRequest<T>> {
        return queueNew.headFlow.filterNotNull()
    }

    fun observeCancelRequest(): Flow<List<UiRequest<T>>> {
        return queueCancel.queueFlow
    }

    fun consumeNewRequest(request: UiRequest<T>) {
        queueNew.remove(request)
    }

    fun consumeCancelRequest(request: UiRequest<T>) {
        queueCancel.remove(request)
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
        queueNew.add(request)
    }

    private fun completeRequest(request: UiRequest<T>) {
        queueNew.remove(request)
        queueCancel.add(request)
    }
}