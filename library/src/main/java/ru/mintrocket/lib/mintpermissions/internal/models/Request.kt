package ru.mintrocket.lib.mintpermissions.internal.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import java.util.*

@Parcelize
internal data class Request(
    val key: UUID,
    val permissions: List<MintPermission>,
) : Parcelable