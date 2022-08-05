package ru.mintrocket.lib.mintpermissions.flows.ui

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mintrocket.lib.mintpermissions.flows.models.DialogContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequestContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult
import kotlin.coroutines.resume

class DefaultDialogContentConsumerImpl : DialogContentConsumer {

    override suspend fun request(
        activity: ComponentActivity,
        request: DialogRequestContent
    ): DialogResult = suspendCancellableCoroutine { continuation ->
        val content = request.content
        val dialog = createDialog(activity, content,
            positiveButtonClicked = { continuation.resume(DialogResult.ACTION) },
            onCancelled = { continuation.resume(DialogResult.CANCEL) }
        )
        dialog.show()

        continuation.invokeOnCancellation {
            dialog.dismiss()
        }
    }

    private fun createDialog(
        context: Context,
        content: DialogContent,
        positiveButtonClicked: () -> Unit,
        onCancelled: () -> Unit,
    ): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle(content.title)
            .setMessage(content.message)
            .setPositiveButton(content.actionBtn) { _, _ -> positiveButtonClicked() }
            .setNegativeButton(content.cancelBtn) { dialog, _ -> dialog.cancel() }
            .setOnCancelListener { onCancelled() }
            .create()
    }
}