@file:Suppress("unused")

package ru.mintrocket.lib.mintpermissions.flows

import android.app.Application
import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.MintPermissionsConfig
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.flows.internal.MintPermissionsFlowZygote
import ru.mintrocket.lib.mintpermissions.flows.models.FlowConfig
import ru.mintrocket.lib.mintpermissions.models.MintPermission

object MintPermissionsFlow {

    /**
     * Always return same instance.
     * You can use it everywhere, activities, viewmodels, repos, etc.
     *
     * @return single instance of [MintPermissionsDialogFlow]
     */
    val dialogFlow: MintPermissionsDialogFlow by lazy {
        MintPermissionsFlowZygote.dialogsFlow
    }

    /**
     * Always return new instance.
     * You can use it everywhere, activities, viewmodels, repos, etc.
     *
     * @return new instance of [MintPermissionsPlainFlow]
     */
    fun createPlainFlow(
        permissions: List<MintPermission>,
        config: FlowConfig? = null
    ): MintPermissionsPlainFlow {
        return MintPermissionsFlowZygote.createPlainFlow(permissions, config)
    }

    /**
     * Always return same instance.
     * May be needs for your custom flow/
     * You can use it everywhere, activities, viewmodels, repos, etc.
     *
     * @return single instance of [DialogsController]
     */
    val dialogsController: DialogsController by lazy {
        MintPermissionsFlowZygote.dialogsController
    }

    /**
     * Always return same instance.
     * May be needs for your custom flow/
     * You can use it everywhere, activities, viewmodels, repos, etc.
     *
     * @return single instance of [AppSettingsController]
     */
    val appSettingsController: AppSettingsController by lazy {
        MintPermissionsFlowZygote.settingsController
    }

    /**
     * Always return same instance.
     * May be needs for your custom flow/
     *
     * @return single instance of [MintPermissionsController]
     */
    val defaultDialogConfig: FlowConfig = MintPermissionsFlowZygote.defaultDialogConfig

    /**
     * Always return same instance.
     * May be needs for your custom flow/
     *
     * @return single instance of [MintPermissionsController]
     */
    val defaultPlainConfig: FlowConfig = MintPermissionsFlowZygote.defaultPlainConfig

    /**
     * Should be initialized once on create application
     *
     * @receiver instance of your [Application]
     * @param config config for internal components
     */
    fun init(application: Application, config: MintPermissionsConfig? = null) {
        MintPermissionsFlowZygote.init(application, config ?: MintPermissionsConfig())
    }

    /**
     * Init manager and internal components.
     * Should be initialized once per activity
     *
     * @receiver instance of [ComponentActivity]
     * @throws Exception then initialized multiple times
     */
    fun createManager(): MintPermissionsFlowManager {
        return MintPermissionsFlowZygote.createManager()
    }
}