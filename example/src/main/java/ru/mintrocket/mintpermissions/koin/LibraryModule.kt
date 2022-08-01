package ru.mintrocket.mintpermissions.koin

import android.Manifest
import org.koin.dsl.module
import ru.mintrocket.lib.mintpermissions.MintPermissions
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsFlow

val libraryModule = module {
    single { MintPermissionsFlow.dialogFlow }
    single { MintPermissions.controller }
    factory { MintPermissions.createManager() }
    factory { MintPermissionsFlow.createManager() }

    // for example, plain flow can be in your DI graph as factory
    factory(Qualifiers.cameraFlowQualifier) {
        val cameraPermissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        MintPermissionsFlow.createPlainFlow(cameraPermissions)
    }

    // this needs only for your custom flow
    single { MintPermissionsFlow.dialogsController }
    single { MintPermissionsFlow.appSettingsController }
}