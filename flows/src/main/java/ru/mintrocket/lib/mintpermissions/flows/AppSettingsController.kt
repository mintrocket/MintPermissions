package ru.mintrocket.lib.mintpermissions.flows

import ru.mintrocket.lib.mintpermissions.flows.models.FlowResultStatus

/**
 * You may need this controller if you need to make your own flow
 */
public interface AppSettingsController {

    /**
     * Opens the application settings and waits for the user to return to the application
     */
    public suspend fun open()
}