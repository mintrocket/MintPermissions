package ru.mintrocket.mintpermissions.dialogs_flow

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsDialogFlow
import ru.mintrocket.lib.mintpermissions.flows.ext.isSuccess
import ru.mintrocket.lib.mintpermissions.flows.models.FlowResultStatus
import ru.mintrocket.mintpermissions.common.Event

class DialogsViewModel(
    private val permissionsDialogFlow: MintPermissionsDialogFlow
) : ViewModel() {

    companion object {
        private val cameraPermissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )
    }

    private val _grantedEvent = MutableStateFlow<Event<Unit>?>(null)

    val grantedEvent = _grantedEvent.asStateFlow()

    fun onActionClick() {
        viewModelScope.launch {
            val result = permissionsDialogFlow.request(cameraPermissions)
            if (result.isSuccess()) {
                _grantedEvent.value = Event(Unit)
            }
        }
    }

}