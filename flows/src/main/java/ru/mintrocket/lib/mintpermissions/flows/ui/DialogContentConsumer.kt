package ru.mintrocket.lib.mintpermissions.flows.ui

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequestContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult

interface DialogContentConsumer {

    suspend fun request(
        activity: ComponentActivity,
        request: DialogRequestContent
    ): DialogResult
}