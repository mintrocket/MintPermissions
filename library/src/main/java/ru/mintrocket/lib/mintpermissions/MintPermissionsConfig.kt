package ru.mintrocket.lib.mintpermissions

/**
 * Mint permissions config
 *
 * @property autoInitManagers flag for auto creating and init [MintPermissionsManager] in all [androidx.activity.ComponentActivity]
 * @constructor Create empty Mint permissions config
 */
public data class MintPermissionsConfig(
    val autoInitManagers: Boolean = true
)
