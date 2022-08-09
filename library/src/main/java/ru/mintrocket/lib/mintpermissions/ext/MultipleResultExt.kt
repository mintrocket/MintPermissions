package ru.mintrocket.lib.mintpermissions.ext

import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult


/* Any flags */
public fun List<MintPermissionResult>.isAnyGranted(): Boolean {
    return any { it.isGranted() }
}

public fun List<MintPermissionResult>.isAnyDenied(): Boolean {
    return any { it.isDenied() }
}

public fun List<MintPermissionResult>.isAnyNeedsRationale(): Boolean {
    return any { it.isNeedsRationale() }
}

public fun List<MintPermissionResult>.isAnyNotFound(): Boolean {
    return any { it.isNotFound() }
}


/* All flags */
public fun List<MintPermissionResult>.isAllGranted(): Boolean {
    return all { it.isGranted() }
}

public fun List<MintPermissionResult>.isAllDenied(): Boolean {
    return all { it.isDenied() }
}

public fun List<MintPermissionResult>.isAllNeedsRationale(): Boolean {
    return all { it.isNeedsRationale() }
}

public fun List<MintPermissionResult>.isAllNotFound(): Boolean {
    return all { it.isNotFound() }
}


/* Filters */
public fun List<MintPermissionResult>.filterGranted(): List<MintPermissionResult> {
    return filter { it.isGranted() }
}

public fun List<MintPermissionResult>.filterDenied(): List<MintPermissionResult> {
    return filter { it.isDenied() }
}

public fun List<MintPermissionResult>.filterNeedsRationale(): List<MintPermissionResult> {
    return filter { it.isNeedsRationale() }
}

public fun List<MintPermissionResult>.filterNotFound(): List<MintPermissionResult> {
    return filter { it.isNotFound() }
}


/* Filters not */
public fun List<MintPermissionResult>.filterNotGranted(): List<MintPermissionResult> {
    return filterNot { it.isGranted() }
}

public fun List<MintPermissionResult>.filterNotDenied(): List<MintPermissionResult> {
    return filterNot { it.isDenied() }
}

public fun List<MintPermissionResult>.filterNotNeedsRationale(): List<MintPermissionResult> {
    return filterNot { it.isNeedsRationale() }
}

public fun List<MintPermissionResult>.filterNotNotFound(): List<MintPermissionResult> {
    return filterNot { it.isNotFound() }
}