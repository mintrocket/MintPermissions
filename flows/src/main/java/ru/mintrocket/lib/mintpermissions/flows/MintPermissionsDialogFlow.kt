package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.flows.models.FlowConfig
import ru.mintrocket.lib.mintpermissions.flows.models.FlowResultStatus
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

/**
 * Mint permissions dialog flow
 * This controller can be used for those cases when there is simply some kind of button on which an
 * action must occur that requires permission.
 *
 * The behavior may change depending on the configuration being passed.
 * The controller itself shows a dialog when it needs to give the user an explanation of what this permission is for.
 * A dialog is also shown with a suggestion to go to the application settings
 */
interface MintPermissionsDialogFlow {

    /**
     * Request single permission
     *
     * @param permission permission from [android.Manifest.permission]
     * @param config permission from [android.Manifest.permission]
     * @return status [FlowResultStatus]. Returns [FlowResultStatus.SUCCESS] if all permissions was granted.
     */
    suspend fun request(
        permission: MintPermission,
        config: FlowConfig? = null
    ): FlowResultStatus

    /**
     * Request multiple permission
     *
     * @param permissions list of permission from [android.Manifest.permission]
     * @param config permission from [android.Manifest.permission]
     * @return status [FlowResultStatus]. Returns [FlowResultStatus.SUCCESS] if all permissions was granted.
     */
    suspend fun request(
        permissions: List<MintPermission>,
        config: FlowConfig? = null
    ): FlowResultStatus
}