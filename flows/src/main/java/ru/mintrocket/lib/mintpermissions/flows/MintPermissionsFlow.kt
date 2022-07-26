package ru.mintrocket.lib.mintpermissions.flows

import android.app.Application
import ru.mintrocket.lib.mintpermissions.MintPermissionsConfig
import ru.mintrocket.lib.mintpermissions.flows.uirequests.MintPermissionsFlowManager
import ru.mintrocket.lib.mintpermissions.flows.uirequests.MintPermissionsFlowZygote
import ru.mintrocket.lib.mintpermissions.models.MintPermission

object MintPermissionsFlow {

    val controller: MintPermissionsDialogFlow by lazy {
        MintPermissionsFlowZygote.dialogsFlow
    }

    fun init(application: Application, config: MintPermissionsConfig? = null) {
        MintPermissionsFlowZygote.init(application, config ?: MintPermissionsConfig())
    }

    fun createPlainFlow(permissions: List<MintPermission>): MintPermissionsPlainFlow {
        return MintPermissionsFlowZygote.createPlainFlow(permissions)
    }

    fun createManager(): MintPermissionsFlowManager {
        return MintPermissionsFlowZygote.createManager()
    }
}