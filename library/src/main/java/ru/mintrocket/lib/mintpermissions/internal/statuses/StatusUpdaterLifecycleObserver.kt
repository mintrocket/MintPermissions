package ru.mintrocket.lib.mintpermissions.internal.statuses

import android.os.Build
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

@Suppress("FunctionName")
internal fun StatusUpdaterLifecycleObserver(
    activity: ComponentActivity,
    activeListener: (Boolean) -> Unit
): LifecycleObserver {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        return StatusUpdaterLifecycleObserverV16(activity, activeListener)
    } else {
        return StatusUpdaterLifecycleObserverV15(activeListener)
    }
}

private class StatusUpdaterLifecycleObserverV15(
    private val activeListener: (Boolean) -> Unit
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        activeListener(true)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        activeListener(true)
    }
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
private class StatusUpdaterLifecycleObserverV16(
    private val activity: ComponentActivity,
    private val activeListener: (Boolean) -> Unit
) : DefaultLifecycleObserver {

    private var currentResumed = false

    private val focusListener = ViewTreeObserver.OnWindowFocusChangeListener {
        if (it && currentResumed) {
            activeListener.invoke(it)
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewTreeObserver().addOnWindowFocusChangeListener(focusListener)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        currentResumed = true
        activeListener.invoke(true)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        currentResumed = false
        activeListener.invoke(false)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        viewTreeObserver().removeOnWindowFocusChangeListener(focusListener)
    }

    private fun viewTreeObserver() = activity.window.decorView.viewTreeObserver
}