package ru.mintrocket.lib.mintpermissions.flows

import android.app.Application
import ru.mintrocket.lib.mintpermissions.MintPermissionsConfig
import ru.mintrocket.lib.mintpermissions.flows.internal.MintPermissionsFlowZygote
import ru.mintrocket.lib.mintpermissions.flows.models.FlowConfig
import ru.mintrocket.lib.mintpermissions.models.MintPermission

object MintPermissionsFlow {

    val dialogFlow: MintPermissionsDialogFlow by lazy {
        MintPermissionsFlowZygote.dialogsFlow
    }

    fun createPlainFlow(
        permissions: List<MintPermission>,
        config: FlowConfig? = null
    ): MintPermissionsPlainFlow {
        return MintPermissionsFlowZygote.createPlainFlow(permissions, config)
    }

    val dialogsController: DialogsController by lazy {
        MintPermissionsFlowZygote.dialogsController
    }

    val appSettingsController: AppSettingsController by lazy {
        MintPermissionsFlowZygote.settingsController
    }

    fun init(application: Application, config: MintPermissionsConfig? = null) {
        MintPermissionsFlowZygote.init(application, config ?: MintPermissionsConfig())
    }

    fun createManager(): MintPermissionsFlowManager {
        return MintPermissionsFlowZygote.createManager()
    }
}