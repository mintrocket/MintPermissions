package ru.mintrocket.lib.mintpermissions.tools.uirequests

import android.os.Parcelable
import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest

public interface UiRequestConsumer<T : Parcelable, R> {

    public suspend fun request(activity: ComponentActivity, request: UiRequest<T>): R
}