package ru.mintrocket.lib.mintpermissions.flows.model

import ru.mintrocket.lib.mintpermissions.tools.uirequests.model.UiRequest

data class DialogRequestContent(
    val request: UiRequest<DialogRequest>,
    val content: DialogContent
)
