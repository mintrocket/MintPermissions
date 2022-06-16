package ru.mintrocket.lib.mintpermissions.models

/**
 * Mint permission status
 *
 * @property permission from [android.Manifest.permission]
 * @constructor Create empty Mint permission status
 */
sealed class MintPermissionStatus(open val permission: MintPermission) {

    /**
     * Granted
     * Used when the app has this permission
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Granted
     */
    data class Granted(
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
    data class Denied(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)

    /**
     * Needs rationale
     * Used when you need to explain what this permission is used for
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Needs rationale
     */
    data class NeedsRationale(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)

    /**
     * Not found
     * Used when permission not found in declared permissions in app manifest
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Not found
     */
    data class NotFound(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)
}
