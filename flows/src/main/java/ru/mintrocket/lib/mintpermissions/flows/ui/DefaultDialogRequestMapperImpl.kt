package ru.mintrocket.lib.mintpermissions.flows.ui

import android.content.Context
import ru.mintrocket.lib.mintpermissions.flows.R
import ru.mintrocket.lib.mintpermissions.flows.models.DialogContent
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequest
import ru.mintrocket.lib.mintpermissions.flows.models.DialogRequestGroup
import ru.mintrocket.lib.mintpermissions.models.MintPermission

class DefaultDialogRequestMapperImpl : DialogContentMapper {

    private val regexName by lazy { Regex("android\\.permission\\.(\\w+)") }

    override suspend fun map(context: Context, request: DialogRequest): DialogContent {
        val permissionNames = request.results.joinToString {
            makeNameForText(it.status.permission)
        }

        val title = context.getString(R.string.mint_flow_dialog_title, permissionNames)

        val message = when (request.group) {
            DialogRequestGroup.NEEDS_RATIONALE -> {
                context.getString(R.string.mint_flow_dialog_rationale_message, permissionNames)
            }
            DialogRequestGroup.DENIED -> {
                context.getString(R.string.mint_flow_dialog_denied_message, permissionNames)
            }
        }

        val positive = when (request.group) {
            DialogRequestGroup.NEEDS_RATIONALE -> {
                context.getString(R.string.mint_flow_dialog_rationale_action)
            }
            DialogRequestGroup.DENIED -> {
                context.getString(R.string.mint_flow_dialog_denied_action)
            }
        }

        val negative = context.getString(R.string.mint_flow_dialog_cancel)

        return DialogContent(title, message, positive, negative)
    }

    private fun makeNameForText(permission: MintPermission): String {
        val permName = regexName.find(permission)?.groups?.get(1)?.value
        requireNotNull(permName) {
            "Unsupported permission for mapping $permission"
        }

        val words = permName.split('_')
        return words.joinToString(" ") { word ->
            word.lowercase()
        }
    }
}