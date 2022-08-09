package ru.mintrocket.lib.mintpermissions.ext

import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus


/* Any flags */
public fun List<MintPermissionStatus>.isAnyGranted(): Boolean {
    return any { it.isGranted() }
}

public fun List<MintPermissionStatus>.isAnyDenied(): Boolean {
    return any { it.isDenied() }
}

public fun List<MintPermissionStatus>.isAnyNeedsRationale(): Boolean {
    return any { it.isNeedsRationale() }
}

public fun List<MintPermissionStatus>.isAnyNotFound(): Boolean {
    return any { it.isNotFound() }
}


/* All flags */
public fun List<MintPermissionStatus>.isAllGranted(): Boolean {
    return all { it.isGranted() }
}

public fun List<MintPermissionStatus>.isAllDenied(): Boolean {
    return all { it.isDenied() }
}

public fun List<MintPermissionStatus>.isAllNeedsRationale(): Boolean {
    return all { it.isNeedsRationale() }
}

public fun List<MintPermissionStatus>.isAllNotFound(): Boolean {
    return all { it.isNotFound() }
}


/* Filters */
public fun List<MintPermissionStatus>.filterGranted(): List<MintPermissionStatus> {
    return filter { it.isGranted() }
}

public fun List<MintPermissionStatus>.filterDenied(): List<MintPermissionStatus> {
    return filter { it.isDenied() }
}

public fun List<MintPermissionStatus>.filterNeedsRationale(): List<MintPermissionStatus> {
    return filter { it.isNeedsRationale() }
}

public fun List<MintPermissionStatus>.filterNotFound(): List<MintPermissionStatus> {
    return filter { it.isNotFound() }
}


/* Filters not */
public fun List<MintPermissionStatus>.filterNotGranted(): List<MintPermissionStatus> {
    return filterNot { it.isGranted() }
}

public fun List<MintPermissionStatus>.filterNotDenied(): List<MintPermissionStatus> {
    return filterNot { it.isDenied() }
}

public fun List<MintPermissionStatus>.filterNotNeedsRationale(): List<MintPermissionStatus> {
    return filterNot { it.isNeedsRationale() }
}

public fun List<MintPermissionStatus>.filterNotNotFound(): List<MintPermissionStatus> {
    return filterNot { it.isNotFound() }
}