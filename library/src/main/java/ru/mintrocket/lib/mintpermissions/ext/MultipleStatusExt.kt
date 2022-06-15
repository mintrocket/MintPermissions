package ru.mintrocket.lib.mintpermissions.ext

import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus


/* Any flags */
fun List<MintPermissionStatus>.isAnyGranted() = any { it.isGranted() }

fun List<MintPermissionStatus>.isAnyDenied() = any { it.isDenied() }

fun List<MintPermissionStatus>.isAnyNeedsRationale() = any { it.isNeedsRationale() }

fun List<MintPermissionStatus>.isAnyNotFound() = any { it.isNotFound() }


/* All flags */
fun List<MintPermissionStatus>.isAllGranted() = all { it.isGranted() }

fun List<MintPermissionStatus>.isAllDenied() = all { it.isDenied() }

fun List<MintPermissionStatus>.isAllNeedsRationale() = all { it.isNeedsRationale() }

fun List<MintPermissionStatus>.isAllNotFound() = all { it.isNotFound() }


/* Filters */
fun List<MintPermissionStatus>.filterGranted() = filter { it.isGranted() }

fun List<MintPermissionStatus>.filterDenied() = filter { it.isDenied() }

fun List<MintPermissionStatus>.filterNeedsRationale() = filter { it.isNeedsRationale() }

fun List<MintPermissionStatus>.filterNotFound() = filter { it.isNotFound() }


/* Filters not */
fun List<MintPermissionStatus>.filterNotGranted() = filterNot { it.isGranted() }

fun List<MintPermissionStatus>.filterNotDenied() = filterNot { it.isDenied() }

fun List<MintPermissionStatus>.filterNotNeedsRationale() = filterNot { it.isNeedsRationale() }

fun List<MintPermissionStatus>.filterNotNotFound() = filterNot { it.isNotFound() }