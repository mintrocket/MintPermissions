package ru.mintrocket.lib.mintpermissions.flows

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequestContent

interface DialogContentConsumer {

    suspend fun request(
        activity: ComponentActivity,
        request: DialogRequestContent
    ): DialogResult
}