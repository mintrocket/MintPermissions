package ru.mintrocket.lib.mintpermissions.flows.internal


import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentConsumer
import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentMapper
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequestContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest

internal class DialogsConsumerImpl(
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