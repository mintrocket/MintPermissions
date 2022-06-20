package ru.mintrocket.lib.mintpermissions.flows


import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.model.UiRequest
import kotlin.coroutines.resume

class DialogsConsumer : UiRequestConsumer<List<MintPermissionResult>, DialogResult> {

    override suspend fun request(
        activity: ComponentActivity,
        request: UiRequest<List<MintPermissionResult>>
    ): DialogResult {
        return suspendCancellableCoroutine { continuation ->
            val dialog = AlertDialog.Builder(activity)
                .setMessage("${request.data.map { it.status }}")
                .setPositiveButton("ACTION") { _, _ ->
                    continuation.resume(DialogResult.ACTION)
                }
                .setNegativeButton("CANCEL") { dialog, _ ->
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
}