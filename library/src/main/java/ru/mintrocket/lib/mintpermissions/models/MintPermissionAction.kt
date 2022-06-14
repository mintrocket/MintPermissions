package ru.mintrocket.lib.mintpermissions.models

sealed class MintPermissionAction(open val permission: MintPermission) {

    data class Granted(
        override val permission: MintPermission
    ) : MintPermissionAction(permission)

    data class NeedsRationale(
        override val permission: MintPermission
    ) : MintPermissionAction(permission)

    data class DeniedPermanently(
        override val permission: MintPermission
    ) : MintPermissionAction(permission)
}
