package ru.mintrocket.lib.mintpermissions.flows.models

import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentConsumer
import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentMapper

/**
 * Flow config
 *
 * Used to customize the behavior of various flow
 *
 * @property showGroupedByStatus if enabled, then needsrationale is requested first, then denied, and then all at once.
 * Otherwise, it just asks for everything at once.
 *
 * @property showNeedsRationale if enabled, it shows the needs rationale dialog.
 *
 * @property checkBeforeSettings if enabled, then before the user is sent to the settings,
 * one more attempt is made to obtain permissions.
 *
 * @property contentMapper for custom content mapper for dialog
 * @property contentConsumer for custom consumer (for example, you can show BottomSheetDialog)
 * @constructor Create empty [DialogContent]
 */
public data class FlowConfig(
    val showGroupedByStatus: Boolean = true,
    val showNeedsRationale: Boolean = true,
    val checkBeforeSettings: Boolean = false,
    val contentMapper: DialogContentMapper? = null,
    val contentConsumer: DialogContentConsumer? = null
)