package ru.mintrocket.lib.mintpermissions.internal.models

import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult

internal data class RequestResult(
    val request: Request,
    val results: List<MintPermissionResult>
)