package ru.mintrocket.lib.mintpermissions.flows

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mintrocket.lib.mintpermissions.flows.model.DialogRequestContent
import ru.mintrocket.lib.mintpermissions.flows.model.DialogResult
import kotlin.coroutines.resume

class DefaultDialogContentConsumerImpl : DialogContentConsumer {

    override suspend fun request(
        activity: ComponentActivity,
        request: DialogRequestContent
    ): DialogResult = suspendCancellableCoroutine { continuation ->
        val content = request.content
        val dialog = AlertDialog.Builder(activity)
            .setTitle(content.title)
            .setMessage(content.message)
            .setPositiveButton(content.positiveBtn) { _, _ ->
                continuation.resume(DialogResult.ACTION)
            }
            .setNegativeButton(content.negativeBtn) { dialog, _ ->
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