package ru.mintrocket.lib.mintpermissions.models

data class MintPermissionResult(
    val status: MintPermissionStatus,
    val action: MintPermissionAction?
)
