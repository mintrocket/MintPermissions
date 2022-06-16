package ru.mintrocket.mintpermissions.koin

import org.koin.dsl.module
import ru.mintrocket.lib.mintpermissions.MintPermissions

val libraryModule = module {
    single { MintPermissions.controller }
    factory { MintPermissions.createManager() }
}