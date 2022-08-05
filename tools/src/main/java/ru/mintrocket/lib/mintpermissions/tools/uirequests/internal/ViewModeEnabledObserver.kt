package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

internal class ViewModeEnabledObserver(
    private val viewModel: UiRequestViewModel<*, *>
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.setEnabled(true)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        viewModel.setEnabled(false)
    }
}