package ru.mintrocket.lib.mintpermissions.internal.requests

import kotlinx.coroutines.flow.Flow
import ru.mintrocket.lib.mintpermissions.internal.models.Request
import ru.mintrocket.lib.mintpermissions.internal.models.RequestResult

internal interface RequestsController {

    fun observeNewRequest(): Flow<Request>

    fun observeCancelRequest(): Flow<Request>

    suspend fun consumeRequest(request: Request)

    suspend fun sendResult(result: RequestResult)

    suspend fun request(request: Request): RequestResult
}