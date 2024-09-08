package ru.mintrocket.lib.mintpermissions.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Mint permission status
 *
 * @property permission from [android.Manifest.permission]
 * @constructor Create empty Mint permission status
 */
public sealed class MintPermissionStatus(public open val permission: MintPermission) : Parcelable {

    /**
     * Granted
     * Used when the app has this permission
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Granted
     */
    @Parcelize
    public data class Granted(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)

    /**
     * Denied
     * Used when the application does not have this permission.
     * This status is the same for "never requested" and "permanently denied" cases.
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Denied
     */
    @Parcelize
    public data class Denied(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)

    /**
     * Needs rationale
     * Used when you need to explain what this permission is used for
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Needs rationale
     */
    @Parcelize
    public data class NeedsRationale(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)

    /**
     * Not found
     * Used when permission not found in declared permissions in app manifest
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Not found
     */
    @Parcelize
    public data class NotFound(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)
}
