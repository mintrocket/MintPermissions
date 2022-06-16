package ru.mintrocket.mintpermissions.koin

import ru.mintrocket.lib.mintpermissions.MintPermissions
import org.koin.dsl.module

val libraryModule = module {
    single { MintPermissions.controller }
    factory { MintPermissions.createManager() }
}