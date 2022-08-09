package ru.mintrocket.lib.mintpermissions.flows.models

import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest

/**
 * Dialog request content
 *
 * @property request may be useful for something in [DialogContentConsumer], idk
 * @property content for show in dialog
 * @constructor Create empty [DialogRequestContent]
 */
public data class DialogRequestContent(
    val request: UiRequest<DialogRequest>,
    val content: DialogContent
)
