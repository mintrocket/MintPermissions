package ru.mintrocket.lib.mintpermissions.flows.internal

import android.app.Application
import ru.mintrocket.lib.mintpermissions.MintPermissions
import ru.mintrocket.lib.mintpermissions.MintPermissionsConfig
import ru.mintrocket.lib.mintpermissions.ext.initMintPermissionsManager
import ru.mintrocket.lib.mintpermissions.flows.ui.DefaultDialogContentConsumerImpl
import ru.mintrocket.lib.mintpermissions.flows.ui.DefaultDialogRequestMapperImpl
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.tools.initializer.ManagerAutoInitializer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestZygote

internal object MintPermissionsFlowZygote {

    private const val KEY_REQUESTS_SETTINGS = "mintpermissions_settings"
    private const val KEY_REQUESTS_DIALOGS = "mintpermissions_dialogs"

    private val dialogsConsumer by lazy {
        val consumer = DefaultDialogContentConsumerImpl()
        val mapper = DefaultDialogRequestMapperImpl()
        DialogsConsumerImpl(consumer, mapper)
    }
    private val dialogsZygote by lazy { UiRequestZygote(KEY_REQUESTS_DIALOGS, dialogsConsumer) }
    val dialogsController by lazy { DialogsControllerImpl(dialogsZygote.controller) }

    private val settingsConsumer by lazy { AppSettingsConsumerImpl() }
    private val settingsZygote by lazy { UiRequestZygote(KEY_REQUESTS_SETTINGS, settingsConsumer) }
    val settingsController by lazy { AppSettingsControllerImpl(settingsZygote.controller) }

    val dialogsFlow by lazy {
        MintPermissionsDialogFlowImpl(
            FlowConfig(),
            MintPermissions.controller,
            dialogsController,
            settingsController
        )
    }

    fun createPlainFlow(permissions: List<MintPermission>): MintPermissionsPlainFlowImpl {
        return MintPermissionsPlainFlowImpl(
            permissions,
            FlowConfig(
                showNeedsRationale = false,
                checkBeforeSettings = false
            ),
            MintPermissions.controller,
            dialogsFlow
        )
    }

    fun init(application: Application, config: MintPermissionsConfig) {
        ManagerAutoInitializer.init(application)
        if (config.autoInitManagers) {
            ManagerAutoInitializer.addInitializer {
                it.initMintPermissionsManager()
            }
        }
    }

    fun createManager(): MintPermissionsFlowManagerImpl = MintPermissionsFlowManagerImpl(
        dialogsZygote.createManager(),
        settingsZygote.createManager()
    )
}

