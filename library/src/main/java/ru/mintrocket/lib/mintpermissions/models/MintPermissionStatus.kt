package ru.mintrocket.lib.mintpermissions.models

sealed class MintPermissionStatus(open val permission: MintPermission) {

    data class Granted(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)

    data class Denied(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)

    data class NeedsRationale(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)

    data class NotFound(
        override val permission: MintPermission
    ) : MintPermissionStatus(permission)
}
