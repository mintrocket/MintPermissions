package ru.mintrocket.lib.mintpermissions.flows.models

import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentConsumer
import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentMapper
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult

/**
 * Dialog request
 *
 * @property group to understand what content to show
 * @property results results from request
 * @property contentMapper for custom content mapper for dialog
 * @property contentConsumer for custom consumer (for example, you can show BottomSheetDialog)
 * @constructor Create empty [DialogRequest]
 */
data class DialogRequest(
    val group: DialogRequestGroup,
    val results: List<MintPermissionResult>,
    val contentMapper: DialogContentMapper?,
    val contentConsumer: DialogContentConsumer?
)