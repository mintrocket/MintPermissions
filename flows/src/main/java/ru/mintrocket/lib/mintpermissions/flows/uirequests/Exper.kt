package ru.mintrocket.lib.mintpermissions.flows.uirequests

import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.mintrocket.lib.mintpermissions.MintPermissions
import ru.mintrocket.lib.mintpermissions.flows.*
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerInitializer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestController
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestZygote
import ru.mintrocket.lib.mintpermissions.tools.uirequests.model.UiRequest
import kotlin.coroutines.resume

object SomeLib {

    private const val KEY_REQUESTS_SETTINGS = "mintpermissions_settings"
    private const val KEY_REQUESTS_DIALOGS = "mintpermissions_dialogs"

    private val dialogsConsumer by lazy { DialogsConsumer() }
    private val dialogsZygote by lazy { UiRequestZygote(KEY_REQUESTS_DIALOGS, dialogsConsumer) }
    private val dialogsController by lazy { DialogsController(dialogsZygote.controller) }

    private val settingsConsumer by lazy { AppSettingsConsumer() }
    private val settingsZygote by lazy { UiRequestZygote(KEY_REQUESTS_SETTINGS, settingsConsumer) }
    private val settingsController by lazy { AppSettingsLauncher(settingsZygote.controller) }

    val controller by lazy {
        MintPermissionsDialogFlowImpl(
            dialogsController,
            MintPermissions.controller,
            settingsController
        )
    }

    fun createManager(): SomeLibManager = SomeLibManager(
        dialogsZygote.createManager(),
        settingsZygote.createManager()
    )
}

class SomeLibManager(
    private val dialogsManager: ManagerInitializer,
    private val settingsManager: ManagerInitializer,
) {

    fun init(activity: ComponentActivity) {
        dialogsManager.init(activity)
        settingsManager.init(activity)
    }
}

