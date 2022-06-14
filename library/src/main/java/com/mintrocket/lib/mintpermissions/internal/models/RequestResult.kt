package com.mintrocket.lib.mintpermissions.internal.models

import com.mintrocket.lib.mintpermissions.models.MintPermissionMultipleResult

internal data class RequestResult(
    val request: Request,
    val result: MintPermissionMultipleResult
)