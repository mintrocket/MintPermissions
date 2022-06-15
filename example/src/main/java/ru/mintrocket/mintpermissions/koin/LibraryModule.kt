package ru.mintrocket.mintpermissions.koin

import ru.mintrocket.lib.mintpermissions.MintPermissionsInitializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val libraryModule = module {
    single { MintPermissionsInitializer.permissionsController }
    factory { MintPermissionsInitializer.createManager() }
}