package ru.mintrocket.lib.mintpermissions.ext

import ru.mintrocket.lib.mintpermissions.models.MintPermissionAction

public fun MintPermissionAction.isGranted(): Boolean {
    return this is MintPermissionAction.Granted
}

public fun MintPermissionAction.isNeedsRationale(): Boolean {
    return this is MintPermissionAction.NeedsRationale
}

public fun MintPermissionAction.isDenied(): Boolean {
    return this is MintPermissionAction.DeniedPermanently
}
