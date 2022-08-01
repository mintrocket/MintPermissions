package ru.mintrocket.lib.mintpermissions.flows.models

import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentConsumer
import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentMapper

data class FlowConfig(
    val showGroupedByStatus: Boolean = false,
    val showNeedsRationale: Boolean = true,
    val checkBeforeSettings: Boolean = false,
    val customContentMapper: DialogContentMapper? = null,
    val customContentConsumer: DialogContentConsumer? = null
)