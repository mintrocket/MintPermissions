package ru.mintrocket.lib.mintpermissions.flows

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequestContent
import ru.mintrocket.lib.mintpermissions.flows.model.DialogResult

interface DialogContentConsumer {

    suspend fun request(
        activity: ComponentActivity,
        request: DialogRequestContent
    ): DialogResult
}