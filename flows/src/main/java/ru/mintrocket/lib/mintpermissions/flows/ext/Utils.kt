package ru.mintrocket.lib.mintpermissions.flows.ext

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun ComponentActivity.awaitActivityResult(key: String, intent: Intent) {
    suspendCancellableCoroutine<Unit> { continuation ->
        val resultRegistry = activityResultRegistry
        val contract = ActivityResultContracts.StartActivityForResult()

        val launcher = resultRegistry.register(key, contract) { _ ->
            continuation.resume(Unit)
        }
        launcher.launch(intent)
        continuation.invokeOnCancellation {
            launcher.unregister()
        }
    }
}