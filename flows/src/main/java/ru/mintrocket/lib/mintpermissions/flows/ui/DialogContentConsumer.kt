package ru.mintrocket.lib.mintpermissions.flows.ui

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequestContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult

/**
 * Used to display a dialog. You can override and show for example a custom BottomSheetDialog.
 */
public interface DialogContentConsumer {

    /**
     * Request for show dialog
     *
     * it is recommended to use [kotlinx.coroutines.suspendCancellableCoroutine] so as not to break
     * the life cycle and to avoid memory leaks
     *
     * @param activity provides resumed activity from your app
     * @param request request with content for dialog
     * @return result [DialogResult]
     */
    public suspend fun request(
        activity: ComponentActivity,
        request: DialogRequestContent
    ): DialogResult
}