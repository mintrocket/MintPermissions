package ru.mintrocket.mintpermissions.koin

import org.koin.dsl.module
import ru.mintrocket.lib.mintpermissions.MintPermissions
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsFlow

val libraryModule = module {
    single { MintPermissionsFlow.dialogFlow }
    single { MintPermissions.controller }
    factory { MintPermissions.createManager() }
    factory { MintPermissionsFlow.createManager() }
}