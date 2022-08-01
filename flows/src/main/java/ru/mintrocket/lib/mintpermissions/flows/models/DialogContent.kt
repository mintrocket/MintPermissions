package ru.mintrocket.lib.mintpermissions.flows.models

/**
 * Dialog content
 *
 * @property title for dialog title. Can be null
 * @property message for dialog message.
 * @property actionBtn for dialog action button (by default for "positive" button)
 * @property cancelBtn for dialog cancel button (by default for "negative" button)
 * @constructor Create empty [DialogContent]
 */
data class DialogContent(
    val title: CharSequence?,
    val message: CharSequence,
    val actionBtn: CharSequence,
    val cancelBtn: CharSequence
)
