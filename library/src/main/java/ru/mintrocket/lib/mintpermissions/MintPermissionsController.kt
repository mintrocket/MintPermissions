package ru.mintrocket.lib.mintpermissions

import kotlinx.coroutines.flow.Flow
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

/**
 * Mint permissions controller
 * Controller for interacting with app permissions.
 * All observe(*) and get(*) methods returns "cached" information about permissions statuses.
 */
interface MintPermissionsController {

    /**
     * Observe single permissions statuses
     *
     * @param permission permission to observe from [android.Manifest.permission]
     * @return flow of single [MintPermissionStatus]. If not found in declared permissions, returns [MintPermissionStatus.NotFound]
     */
    fun observe(permission: MintPermission): Flow<MintPermissionStatus>

    /**
     * Observe multiple permissions statuses
     *
     * @param permissions permissions from [android.Manifest.permission]
     * @return flow of list [MintPermissionStatus]. If not found in declared permissions, returns [MintPermissionStatus.NotFound]
     */
    fun observe(permissions: List<MintPermission>): Flow<List<MintPermissionStatus>>

    /**
     * Observe all declared permissions statuses
     *
     * @return flow of list [MintPermissionStatus]
     */
    fun observeAll(): Flow<List<MintPermissionStatus>>

    /**
     * Get single permissions statuses
     *
     * @param permission permission from [android.Manifest.permission]
     * @return flow of single [MintPermissionStatus]. If not found in declared permissions, returns [MintPermissionStatus.NotFound]
     */
    suspend fun get(permission: MintPermission): MintPermissionStatus

    /**
     * Get multiple permissions statuses
     *
     * @param permissions permissions from [android.Manifest.permission]
     * @return flow of list [MintPermissionStatus]. If not found in declared permissions, returns [MintPermissionStatus.NotFound]
     */
    suspend fun get(permissions: List<MintPermission>): List<MintPermissionStatus>

    /**
     * Get all declared permissions statuses
     *
     * @return list of [MintPermissionStatus]
     */
    suspend fun getAll(): List<MintPermissionStatus>

    /**
     * Request single permission
     *
     * @param permission permission from [android.Manifest.permission]
     * @return single [MintPermissionStatus]. If not found in declared permissions, returns [MintPermissionStatus.NotFound]
     */
    suspend fun request(permission: MintPermission): MintPermissionResult

    /**
     * Request multiple permissions
     *
     * @param permissions permissions from [android.Manifest.permission]
     * @return list of [MintPermissionStatus]. If not found in declared permissions, returns [MintPermissionStatus.NotFound]
     */
    suspend fun request(permissions: List<MintPermission>): List<MintPermissionResult>
}