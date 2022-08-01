package ru.mintrocket.lib.mintpermissions.models

/**
 * Mint permission action
 * Used for detect status changes
 *
 * @property permission from [android.Manifest.permission]
 * @constructor Create empty Mint permission action
 */
sealed class MintPermissionAction(open val permission: MintPermission) {

    /**
     * Granted
     * Used when status changed "not granted" -> "granted"
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Granted
     */
    data class Granted(
        override val permission: MintPermission
    ) : MintPermissionAction(permission)

    /**
     * Needs rationale
     * Used when status changed "not needs rationale" -> "needs rationale"
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Needs rationale
     */
    data class NeedsRationale(
        override val permission: MintPermission
    ) : MintPermissionAction(permission)

    /**
     * Denied permanently
     * Used when status changed "not denied" -> "denied"
     *
     * @property permission from [android.Manifest.permission]
     * @constructor Create empty Denied permanently
     */
    data class DeniedPermanently(
        override val permission: MintPermission
    ) : MintPermissionAction(permission)
}
