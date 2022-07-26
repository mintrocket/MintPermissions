package ru.mintrocket.lib.mintpermissions.flows.models

import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest

data class DialogRequestContent(
    val request: UiRequest<DialogRequest>,
    val content: DialogContent
)
