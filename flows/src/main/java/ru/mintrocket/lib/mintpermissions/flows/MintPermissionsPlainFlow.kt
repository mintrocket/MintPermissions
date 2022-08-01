package ru.mintrocket.lib.mintpermissions.flows

import kotlinx.coroutines.flow.Flow
import ru.mintrocket.lib.mintpermissions.flows.models.FlowConfig
import ru.mintrocket.lib.mintpermissions.flows.models.FlowResultStatus
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

/**
 * Mint permissions plain flow
 * This controller can be used for those cases when there is some full-screen content.(for example, a camera)
 * and you need to display the status of permissions right inside the screen.
 *
 * The controller is used internally, only it has the dialog for [MintPermissionStatus.NeedsRationale]
 * disabled by default, because it is assumed that the explanation will be displayed inside the screen.
 */
interface MintPermissionsPlainFlow {

    /**
     * Observe permissions without [MintPermissionStatus.Granted] status
     *
     * @return flow of list [MintPermissionStatus]. All granted when list is empty
     */
    fun observeNotGranted(): Flow<List<MintPermissionStatus>>

    /**
     * Observe first permission without [MintPermissionStatus.Granted] status
     *
     * @return flow nullable [MintPermissionStatus]. All granted when status [MintPermissionStatus] is null
     */
    fun observeFirstNotGranted(): Flow<MintPermissionStatus?>

    /**
     * Request permissions with default [MintPermissionsDialogFlow] behavior
     *
     * @param config [FlowConfig] config for request
     * @return status [FlowResultStatus]. Returns [FlowResultStatus.SUCCESS] if all permissions was granted.
     */
    suspend fun request(config: FlowConfig? = null): FlowResultStatus

    /**
     * Request permissions sequentially with [MintPermissionsDialogFlow].
     *
     * @param config [FlowConfig] config for request
     * @return status [FlowResultStatus]. Returns [FlowResultStatus.SUCCESS] if all permissions was granted.
     */
    suspend fun requestSequentially(config: FlowConfig? = null): FlowResultStatus
}