package ru.mintrocket.lib.mintpermissions.ext

import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult


public fun MintPermissionResult.isGranted(): Boolean {
    return status.isGranted()
}

public fun MintPermissionResult.isDenied(): Boolean {
    return status.isDenied()
}

public fun MintPermissionResult.isNeedsRationale(): Boolean {
    return status.isNeedsRationale()
}

public fun MintPermissionResult.isNotFound(): Boolean {
    return status.isNotFound()
}