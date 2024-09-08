package ru.mintrocket.lib.mintpermissions.tools.uirequests.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
public data class UiRequest<T : Parcelable>(val key: String, val data: T) : Parcelable