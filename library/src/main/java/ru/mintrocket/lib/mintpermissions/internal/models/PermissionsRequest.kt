package ru.mintrocket.lib.mintpermissions.internal.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.mintrocket.lib.mintpermissions.models.MintPermission

@Parcelize
internal data class PermissionsRequest(
    private val list: List<MintPermission>
) : List<MintPermission> by list, Parcelable