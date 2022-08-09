package ru.mintrocket.lib.mintpermissions.models

/**
 * Mint permission status
 *
 * @property permission from [android.Manifest.permission]
 * @constructor Create empty Mint permission status
 */
public sealed class MintPermissionStatus(public open val permission: MintPermission) {

    /**
     * Granted
     * Used when the app has this permission
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Granted
     */
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
    public data class NotFound(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)
}
