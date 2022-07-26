package ru.mintrocket.lib.mintpermissions.flows.models

data class DialogContent(
    val title: CharSequence?,
    val message: CharSequence,
    val positiveBtn: CharSequence,
    val negativeBtn: CharSequence
)
