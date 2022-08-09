package ru.mintrocket.lib.mintpermissions.tools.ext

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/*
* The filter can be useful in cases where the result is returned several times and you need to
* catch only valid data.
*
* For example, if you make a request for permissions and rotate the screen, then invalid data
* may be returned. In this case, if the user gives permission, then valid data will be returned.
* */
public suspend fun <I, O> ComponentActivity.awaitActivityResult(
    contract: ActivityResultContract<I, O>,
    key: String,
    input: I,
    resultFilter: ((O) -> Boolean)? = null
): O = suspendCancellableCoroutine { continuation ->
    val launcher = activityResultRegistry.register(key, contract) { result ->
        val isPassedByFilter = resultFilter?.invoke(result) ?: true
        if (!continuation.isActive || !isPassedByFilter) {
            return@register
        }
        continuation.resume(result)
    }

    launcher.launch(input)
    continuation.invokeOnCancellation {
        launcher.unregister()
    }
}