package ru.mintrocket.lib.mintpermissions.flows


import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequestContent
import ru.mintrocket.lib.mintpermissions.flows.model.DialogResult
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.model.UiRequest

internal class DialogsConsumer(
    private val contentConsumer: DialogContentConsumer,
    private val contentMapper: DialogContentMapper
) : UiRequestConsumer<DialogRequest, DialogResult> {

    override suspend fun request(
        activity: ComponentActivity,
        request: UiRequest<DialogRequest>
    ): DialogResult {
        val actualMapper = request.data.customContentMapper ?: contentMapper
        val actualConsumer = request.data.customContentConsumer ?: contentConsumer
        val content = actualMapper.map(activity, request.data)
        val contentRequest = DialogRequestContent(request, content)
        return actualConsumer.request(activity, contentRequest)
    }
}