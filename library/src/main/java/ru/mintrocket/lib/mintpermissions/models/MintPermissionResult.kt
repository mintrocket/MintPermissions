package ru.mintrocket.lib.mintpermissions.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Mint permission result
 *
 * @property status instance of [MintPermissionStatus]
 * @property action nullable instance of [MintPermissionAction]. Notnull when [MintPermissionStatus] changed after request
 * @constructor Create empty Mint permission result
 */
@Parcelize
public data class MintPermissionResult(
    val status: MintPermissionStatus,
    val action: MintPermissionAction?
) : Parcelable
