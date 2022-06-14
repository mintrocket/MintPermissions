package ru.mintrocket.lib.mintpermissions.ext

import ru.mintrocket.lib.mintpermissions.models.MintPermissionAction

fun MintPermissionAction.isGranted(): Boolean = this is MintPermissionAction.Granted

fun MintPermissionAction.isNeedsRationale(): Boolean = this is MintPermissionAction.NeedsRationale

fun MintPermissionAction.isDenied(): Boolean = this is MintPermissionAction.DeniedPermanently
