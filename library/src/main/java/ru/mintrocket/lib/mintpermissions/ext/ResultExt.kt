package ru.mintrocket.lib.mintpermissions.ext

import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult


fun MintPermissionResult.isGranted(): Boolean = status.isGranted()

fun MintPermissionResult.isDenied(): Boolean = status.isDenied()

fun MintPermissionResult.isNeedsRationale(): Boolean = status.isNeedsRationale()

fun MintPermissionResult.isNotFound(): Boolean = status.isNotFound()