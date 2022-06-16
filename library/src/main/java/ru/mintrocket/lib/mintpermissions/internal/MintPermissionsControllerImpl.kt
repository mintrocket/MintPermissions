package ru.mintrocket.lib.mintpermissions.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.internal.models.Request
import ru.mintrocket.lib.mintpermissions.internal.requests.RequestsController
import ru.mintrocket.lib.mintpermissions.internal.statuses.StatusesController
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus
import java.util.*

internal class MintPermissionsControllerImpl(
    private val requestsController: RequestsController,
    private val statusesController: StatusesController
) : MintPermissionsController {

    override fun observe(permission: MintPermission): Flow<MintPermissionStatus> {
        return statusesController
            .observe()
            .map { it[permission] ?: MintPermissionStatus.NotFound(permission) }
            .distinctUntilChanged()
    }

    override fun observe(permissions: List<MintPermission>): Flow<List<MintPermissionStatus>> {
        return statusesController
            .observe()
            .map { statusMap ->
                permissions.map {
                    statusMap.getStatus(it)
                }
            }
            .distinctUntilChanged()
    }

    override fun observeAll(): Flow<List<MintPermissionStatus>> {
        return statusesController
            .observe()
            .map { it.values.toList() }
            .distinctUntilChanged()
    }

    override suspend fun getAll(): List<MintPermissionStatus> {
        return observeAll().first()
    }

    override suspend fun get(permission: MintPermission): MintPermissionStatus {
        return observe(permission).first()
    }

    override suspend fun get(permissions: List<MintPermission>): List<MintPermissionStatus> {
        return observe(permissions).first()
    }

    override suspend fun request(
        permissions: List<MintPermission>
    ): List<MintPermissionResult> {
        val key = UUID.randomUUID()
        val request = Request(key, permissions)
        return requestsController.request(request).results
    }

    override suspend fun request(permission: MintPermission): MintPermissionResult {
        return request(listOf(permission)).first()
    }

    private fun Map<MintPermission, MintPermissionStatus>.getStatus(permission: MintPermission): MintPermissionStatus {
        return get(permission) ?: MintPermissionStatus.NotFound(permission)
    }
}