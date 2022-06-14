package com.mintrocket.lib.mintpermissions.ext

import com.mintrocket.lib.mintpermissions.models.MintPermissionStatus


fun MintPermissionStatus.isGranted(): Boolean = this is MintPermissionStatus.Granted

fun MintPermissionStatus.isDenied(): Boolean = this is MintPermissionStatus.Denied

fun MintPermissionStatus.isNeedsRationale(): Boolean = this is MintPermissionStatus.NeedsRationale

fun MintPermissionStatus.isNotFound(): Boolean = this is MintPermissionStatus.NotFound