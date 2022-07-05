package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConfig

internal class UiRequestViewModelFactory<T, R>(
    private val config: UiRequestConfig,
    private val controller: UiRequestControllerImpl<T, R>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <V : ViewModel> create(
        key: String,
        modelClass: Class<V>,
        handle: SavedStateHandle
    ): V {
        val viewModel = when (modelClass) {
            UiRequestViewModel::class.java -> UiRequestViewModel(handle, config, controller)
            else -> null
        }

        return requireNotNull(viewModel as? V) {
            "Unsupported $modelClass"
        }
    }
}