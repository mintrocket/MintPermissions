package ru.mintrocket.lib.mintpermissions.tools.uirequests.models

import android.os.Parcelable
import java.io.Serializable

public data class UiResult<T : Parcelable, R>(val request: UiRequest<T>, val data: R) : Serializable