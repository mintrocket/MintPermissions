package ru.mintrocket.lib.mintpermissions.flows.internal.ui

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequestContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult
import ru.mintrocket.lib.mintpermissions.flows.ui.DialogContentConsumer
import kotlin.coroutines.resume

internal class DefaultDialogContentConsumerImpl : DialogContentConsumer {

    override suspend fun request(
        activity: ComponentActivity,
        request: DialogRequestContent
    ): DialogResult = suspendCancellableCoroutine { continuation ->
        val content = request.content
        val dialog = AlertDialog.Builder(activity)
            .setTitle(content.title)
            .setMessage(content.message)
            .setPositiveButton(content.actionBtn) { _, _ ->
                continuation.resume(DialogResult.ACTION)
            }
            .setNegativeButton(content.cancelBtn) { dialog, _ ->
                dialog.cancel()
            }
            .setOnCancelListener {
                continuation.resume(DialogResult.CANCEL)
            }
            .show()

        continuation.invokeOnCancellation {
            dialog.dismiss()
        }
    }
}