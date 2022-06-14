package com.mintrocket.lib.mintpermissions.ext

import com.mintrocket.lib.mintpermissions.models.MintPermissionResult


fun MintPermissionResult.isGranted(): Boolean = status.isGranted()

fun MintPermissionResult.isDenied(): Boolean = status.isDenied()

fun MintPermissionResult.isNeedsRationale(): Boolean = status.isNeedsRationale()

fun MintPermissionResult.isNotFound(): Boolean = status.isNotFound()