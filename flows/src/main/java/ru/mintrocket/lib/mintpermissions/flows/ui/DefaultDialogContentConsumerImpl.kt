package ru.mintrocket.lib.mintpermissions.flows.ui

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mintrocket.lib.mintpermissions.flows.models.DialogContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequestContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult
import kotlin.coroutines.resume

public class DefaultDialogContentConsumerImpl : DialogContentConsumer {

    override suspend fun request(
        activity: ComponentActivity,
        request: DialogRequestContent
    ): DialogResult = suspendCancellableCoroutine { continuation ->
        val content = request.content
        val dialog = createDialog(
            context = activity,
            content = content,
            onPositiveClick = { continuation.resume(DialogResult.ACTION) },
            onCancel = { continuation.resume(DialogResult.CANCEL) }
        )
        dialog.show()

        continuation.invokeOnCancellation {
            dialog.dismiss()
        }
    }

    private fun createDialog(
        context: Context,
        content: DialogContent,
        onPositiveClick: () -> Unit,
        onCancel: () -> Unit,
    ): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle(content.title)
            .setMessage(content.message)
            .setPositiveButton(content.actionBtn) { _, _ -> onPositiveClick() }
            .setNegativeButton(content.cancelBtn) { dialog, _ -> dialog.cancel() }
            .setOnCancelListener { onCancel() }
            .create()
    }
}