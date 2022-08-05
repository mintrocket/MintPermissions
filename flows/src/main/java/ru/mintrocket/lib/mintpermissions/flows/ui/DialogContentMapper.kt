package ru.mintrocket.lib.mintpermissions.flows.ui

import android.content.Context
import ru.mintrocket.lib.mintpermissions.flows.models.DialogContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest

/**
 * Used to map the request to the content for the dialog
 */
public interface DialogContentMapper {

    /**
     * Map request to dialog content
     *
     * @param context for access for string resources, icons, etc.
     * @param request request for dialog
     * @return result [DialogContent]
     */
    public suspend fun map(context: Context, request: DialogRequest): DialogContent
}