package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.models.DialogResult
import ru.mintrocket.lib.mintpermissions.flows.models.FlowResultStatus

/**
 * You may need this controller if you need to make your own flow
 */
interface DialogsController {

    /**
     * Opens a dialog and waits for the user to interact with it
     *
     * @param request [DialogRequest] for dialog
     * @return result [DialogResult]. Returns [DialogResult.ACTION] when action clicked (by default it is "positive" button in alert dialog)
     */
    suspend fun open(request: DialogRequest): DialogResult
}