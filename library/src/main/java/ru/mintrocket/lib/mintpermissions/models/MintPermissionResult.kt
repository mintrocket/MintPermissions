package ru.mintrocket.lib.mintpermissions.models

/**
 * Mint permission result
 *
 * @property status instance of [MintPermissionStatus]
 * @property action nullable instance of [MintPermissionAction]. Notnull when [MintPermissionStatus] changed after request
 * @constructor Create empty Mint permission result
 */
public data class MintPermissionResult(
    val status: MintPermissionStatus,
    val action: MintPermissionAction?
)
