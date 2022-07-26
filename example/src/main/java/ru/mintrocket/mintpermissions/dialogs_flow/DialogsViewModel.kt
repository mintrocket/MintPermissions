package ru.mintrocket.mintpermissions.dialogs_flow

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.flows.FlowResultStatus
import ru.mintrocket.lib.mintpermissions.flows.uirequests.MintPermissionsFlowZygote
import ru.mintrocket.mintpermissions.common.Event

class DialogsViewModel(
    private val permissionsController: MintPermissionsController,
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
            val result = MintPermissionsFlowZygote.dialogsFlow.request(cameraPermissions)
            if (result == FlowResultStatus.SUCCESS) {
                _grantedEvent.value = Event(Unit)
            }
        }
    }

}