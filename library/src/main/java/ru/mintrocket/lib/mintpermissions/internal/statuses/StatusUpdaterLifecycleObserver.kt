package ru.mintrocket.lib.mintpermissions.internal.statuses

import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

internal class StatusUpdaterLifecycleObserver(
    private val activity: ComponentActivity,
    private val updateListener: () -> Unit
) : DefaultLifecycleObserver {

    private val focusListener = ViewTreeObserver.OnWindowFocusChangeListener {
        if (it) {
            updateListener.invoke()
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewTreeObserver().addOnWindowFocusChangeListener(focusListener)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        updateListener.invoke()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        viewTreeObserver().removeOnWindowFocusChangeListener(focusListener)
    }

    private fun viewTreeObserver() = activity.window.decorView.viewTreeObserver
}