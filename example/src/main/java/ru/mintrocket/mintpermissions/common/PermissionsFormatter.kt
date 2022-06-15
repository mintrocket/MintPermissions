package ru.mintrocket.mintpermissions.common

import android.graphics.Color
import android.text.Spanned
import android.text.SpannedString
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import ru.mintrocket.lib.mintpermissions.models.MintPermissionAction
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus

object PermissionsFormatter {

    fun formatStatuses(results: List<MintPermissionStatus>): Spanned {
        if (results.isEmpty()) {
            return SpannedString("Empty")
        }
        return createSpannedStatuses(results.map { it to null })
    }

    fun formatResults(results: List<MintPermissionResult>): Spanned {
        if (results.isEmpty()) {
            return SpannedString("Empty")
        }
        return createSpannedStatuses(results.map { it.status to it.action })
    }

    private fun create(results: List<Pair<MintPermissionStatus, MintPermissionAction?>>): Spanned {
        if (results.isEmpty()) {
            return SpannedString("Empty")
        }
        return createSpannedStatuses(results)
    }

    private fun createSpannedStatuses(results: List<Pair<MintPermissionStatus, MintPermissionAction?>>): Spanned {
        val lastPair = results.lastOrNull()
        return buildSpannedString {
            results.forEach { pair ->
                val (status, action) = pair
                append(status.permission)
                append(":\n")
                color(getColorByStatus(status)) {
                    append(status::class.simpleName)
                }
                if (action != null) {
                    append(" with action ")
                    append(action::class.simpleName)
                }
                if (pair != lastPair) {
                    append("\n\n")
                }
            }
        }
    }

    private fun getColorByStatus(status: MintPermissionStatus): Int {
        return when (status) {
            is MintPermissionStatus.NeedsRationale -> Color.LTGRAY
            is MintPermissionStatus.Denied -> Color.RED
            is MintPermissionStatus.Granted -> Color.GREEN
            is MintPermissionStatus.NotFound -> Color.MAGENTA
        }
    }
}