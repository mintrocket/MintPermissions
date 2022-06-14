package ru.mintrocket.lib.mintpermissions.internal.models

import ru.mintrocket.lib.mintpermissions.models.MintPermissionMultipleResult

internal data class RequestResult(
    val request: Request,
    val result: MintPermissionMultipleResult
)