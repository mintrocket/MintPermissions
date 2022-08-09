package ru.mintrocket.lib.mintpermissions.flows.ext

import ru.mintrocket.lib.mintpermissions.flows.models.FlowResultStatus

public fun FlowResultStatus.isSuccess(): Boolean {
    return this == FlowResultStatus.SUCCESS
}

public fun FlowResultStatus.isCanceled(): Boolean {
    return this == FlowResultStatus.CANCELED
}