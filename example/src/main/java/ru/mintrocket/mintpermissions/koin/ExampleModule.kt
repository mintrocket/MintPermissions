package ru.mintrocket.mintpermissions.koin

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.mintrocket.mintpermissions.content_flow.ContentFlowViewModel
import ru.mintrocket.mintpermissions.data_flow.DataFlowRepository
import ru.mintrocket.mintpermissions.data_flow.DataFlowViewModel
import ru.mintrocket.mintpermissions.dialogs_flow.DialogsViewModel
import ru.mintrocket.mintpermissions.playground.PlaygroundViewModel

val exampleModule = module {
    // Playground
    viewModel { PlaygroundViewModel(get()) }

    // Dialogs
    viewModel { DialogsViewModel(get()) }

    // Content
    viewModel { ContentFlowViewModel() }

    // Data
    single { DataFlowRepository(get()) }
    viewModel { DataFlowViewModel(get()) }
}