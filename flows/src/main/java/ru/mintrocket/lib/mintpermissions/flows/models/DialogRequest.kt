package ru.mintrocket.lib.mintpermissions.flows.models

import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentConsumer
import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentMapper
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult

data class DialogRequest(
    val group: DialogRequestGroup,
    val results: List<MintPermissionResult>,
    val contentMapper: DialogContentMapper?,
    val contentConsumer: DialogContentConsumer?
)