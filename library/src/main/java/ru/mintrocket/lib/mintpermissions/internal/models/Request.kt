package ru.mintrocket.lib.mintpermissions.internal.models

import android.os.Parcelable
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
internal data class Request(
    val key: UUID,
    val permissions: List<MintPermission>,
) : Parcelable