package com.mintrocket.lib.mintpermissions.ext

import com.mintrocket.lib.mintpermissions.models.MintPermissionResult


/* Any flags */
fun List<MintPermissionResult>.isAnyGranted() = any { it.isGranted() }

fun List<MintPermissionResult>.isAnyDenied() = any { it.isDenied() }

fun List<MintPermissionResult>.isAnyNeedsRationale() = any { it.isNeedsRationale() }

fun List<MintPermissionResult>.isAnyNotFound() = any { it.isNotFound() }


/* All flags */
fun List<MintPermissionResult>.isAllGranted() = all { it.isGranted() }

fun List<MintPermissionResult>.isAllDenied() = all { it.isDenied() }

fun List<MintPermissionResult>.isAllNeedsRationale() = all { it.isNeedsRationale() }

fun List<MintPermissionResult>.isAllNotFound() = all { it.isNotFound() }


/* Filters */
fun List<MintPermissionResult>.filterGranted() = filter { it.isGranted() }

fun List<MintPermissionResult>.filterDenied() = filter { it.isDenied() }

fun List<MintPermissionResult>.filterNeedsRationale() = filter { it.isNeedsRationale() }

fun List<MintPermissionResult>.filterNotFound() = filter { it.isNotFound() }


/* Filters not */
fun List<MintPermissionResult>.filterNotGranted() = filterNot { it.isGranted() }

fun List<MintPermissionResult>.filterNotDenied() = filterNot { it.isDenied() }

fun List<MintPermissionResult>.filterNotNeedsRationale() = filterNot { it.isNeedsRationale() }

fun List<MintPermissionResult>.filterNotNotFound() = filterNot { it.isNotFound() }