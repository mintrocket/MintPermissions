package ru.mintrocket.lib.mintpermissions.flows.models

import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentConsumer
import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentMapper

data class FlowConfig(
    val showGroupedByStatus: Boolean = true,
    val showNeedsRationale: Boolean = true,
    val checkBeforeSettings: Boolean = true,
    val customContentMapper: DialogContentMapper? = null,
    val customContentConsumer: DialogContentConsumer? = null
)