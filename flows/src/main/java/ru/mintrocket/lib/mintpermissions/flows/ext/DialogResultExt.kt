package ru.mintrocket.lib.mintpermissions.flows.ext

import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult

public fun DialogResult.isAction(): Boolean {
    return this == DialogResult.ACTION
}

public fun DialogResult.isCancel(): Boolean {
    return this == DialogResult.CANCEL
}