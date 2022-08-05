package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult

/**
 * You may need this controller if you need to make your own flow
 */
public interface DialogsController {

    /**
     * Opens a dialog and waits for the user to interact with it
     *
     * @param request [DialogRequest] for dialog
     * @return result [DialogResult]. Returns [DialogResult.ACTION] when action clicked (by default it is "positive" button in alert dialog)
     */
    public suspend fun open(request: DialogRequest): DialogResult
}