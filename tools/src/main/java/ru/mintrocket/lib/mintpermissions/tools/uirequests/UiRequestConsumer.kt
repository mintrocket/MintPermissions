package ru.mintrocket.lib.mintpermissions.tools.uirequests

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.tools.uirequests.model.UiRequest

interface UiRequestConsumer<T, R> {

    suspend fun request(activity: ComponentActivity, request: UiRequest<T>): R
}