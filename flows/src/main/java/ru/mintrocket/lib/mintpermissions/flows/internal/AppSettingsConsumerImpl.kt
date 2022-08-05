package ru.mintrocket.lib.mintpermissions.flows.internal

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import ru.mintrocket.lib.mintpermissions.tools.ext.awaitActivityResult
import ru.mintrocket.lib.mintpermissions.tools.uirequests.UiRequestConsumer
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest

internal class AppSettingsConsumerImpl : UiRequestConsumer<Unit, Unit> {

    override suspend fun request(activity: ComponentActivity, request: UiRequest<Unit>) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", activity.packageName, null)
        }
        val contract = ActivityResultContracts.StartActivityForResult()
        activity.awaitActivityResult(contract, request.key, intent)
    }
}