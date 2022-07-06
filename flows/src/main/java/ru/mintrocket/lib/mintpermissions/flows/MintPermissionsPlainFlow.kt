package ru.mintrocket.lib.mintpermissions.flows

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterNotGranted
import ru.mintrocket.lib.mintpermissions.ext.isAllGranted
import ru.mintrocket.lib.mintpermissions.ext.isGranted
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

class MintPermissionsPlainFlow(
    private val permissions: List<MintPermission>,
    private val permissionsController: MintPermissionsController,
    private val appSettingsController: AppSettingsController,
    private val dialogsController: DialogsController
) {

    val isAllGrantedFlow = permissionsController
        .observe(permissions)
        .map { it.isAllGranted() }
        .distinctUntilChanged()

    val notGrantedFlow = permissionsController
        .observe(permissions)
        .map { it.filterNotGranted() }
        .distinctUntilChanged()

    val firstNotGrantedFlow = notGrantedFlow
        .map { it.firstOrNull() }
        .distinctUntilChanged()

    suspend fun initialRequest() {
        permissionsController.request(permissions)
    }

    suspend fun requestNext() {
        val firstStatus = firstNotGrantedFlow.first() ?: return
        when (firstStatus) {
            is MintPermissionStatus.NeedsRationale -> {
                permissionsController.request(firstStatus.permission)
            }
            is MintPermissionStatus.Denied -> {
                val result = permissionsController.request(firstStatus.permission)
                if (!result.isGranted()) {
                    appSettingsController.open()
                }
            }
            else -> {
                // do nothing is ok
            }
        }
    }

    private fun handleResult(result: MintPermissionResult) {

    }
}