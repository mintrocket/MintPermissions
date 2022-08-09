package ru.mintrocket.lib.mintpermissions.ext

import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus


public fun MintPermissionStatus.isGranted(): Boolean {
    return this is MintPermissionStatus.Granted
}

public fun MintPermissionStatus.isDenied(): Boolean {
    return this is MintPermissionStatus.Denied
}

public fun MintPermissionStatus.isNeedsRationale(): Boolean {
    return this is MintPermissionStatus.NeedsRationale
}

public fun MintPermissionStatus.isNotFound(): Boolean {
    return this is MintPermissionStatus.NotFound
}