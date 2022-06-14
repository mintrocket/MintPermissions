package com.mintrocket.lib.mintpermissions.internal.requests

import com.mintrocket.lib.mintpermissions.internal.models.Request
import com.mintrocket.lib.mintpermissions.internal.models.RequestResult
import kotlinx.coroutines.flow.Flow

internal interface RequestsController {

    fun observeNewRequest(): Flow<Request>

    fun observeCancelRequest(): Flow<Request>

    suspend fun consumeRequest(request: Request)

    suspend fun sendResult(result: RequestResult)

    suspend fun request(request: Request): RequestResult
}