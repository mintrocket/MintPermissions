package ru.mintrocket.lib.mintpermissions.flows


import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequestContent
import ru.mintrocket.lib.mintpermissions.flows.model.DialogResult
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.model.UiRequest

internal class DialogsConsumer(
    private val contentConsumer: DialogContentConsumer,
    private val mapper: DialogContentMapper
) : UiRequestConsumer<DialogRequest, DialogResult> {

    override suspend fun request(
        activity: ComponentActivity,
        request: UiRequest<DialogRequest>
    ): DialogResult {
        val config = request.data.config
        val content = (config.customContentMapper ?: mapper).map(activity, request.data)
        val contentRequest = DialogRequestContent(request, content)
        return (config.customContentConsumer ?: contentConsumer).request(activity, contentRequest)
    }
}