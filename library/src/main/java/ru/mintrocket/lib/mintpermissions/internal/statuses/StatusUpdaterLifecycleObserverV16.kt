package ru.mintrocket.lib.mintpermissions.internal.statuses

import android.os.Build
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
internal class StatusUpdaterLifecycleObserverV16(
    private val activity: ComponentActivity,
    private val activeListener: (Boolean) -> Unit
) : DefaultLifecycleObserver {

    private val focusListener = ViewTreeObserver.OnWindowFocusChangeListener {
        if (it) {
            activeListener.invoke(it)
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewTreeObserver().addOnWindowFocusChangeListener(focusListener)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        activeListener.invoke(true)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        activeListener.invoke(false)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        viewTreeObserver().removeOnWindowFocusChangeListener(focusListener)
    }

    private fun viewTreeObserver() = activity.window.decorView.viewTreeObserver
}