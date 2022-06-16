package ru.mintrocket.mintpermissions.playground

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult

class PlaygroundViewModel(
    private val permissionsController: MintPermissionsController
) : ViewModel() {

    companion object {
        private val cameraPermissions = listOf(
            Manifest.permission.CAMERA
        )
        private val locationPermissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private val storagePermissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
        private val allPermissions = cameraPermissions + locationPermissions + storagePermissions
    }

    val targetPermissionsFlow = permissionsController.observe(allPermissions)

    val allAppPermissionsFlow = permissionsController.observeAll()

    val latestResult = MutableStateFlow<List<MintPermissionResult>>(emptyList())

    fun requestAll() {
        requestPermissions(allPermissions)
    }

    fun requestAllQueue() {
        allPermissions.forEach {
            requestPermissions(listOf(it))
        }
    }

    fun requestCamera() {
        requestPermissions(cameraPermissions)
    }

    fun requestLocation() {
        requestPermissions(locationPermissions)
    }

    fun requestStorage() {
        requestPermissions(storagePermissions)
    }

    private fun requestPermissions(permissions: List<MintPermission>): Job {
        return viewModelScope.launch {
            val result = permissionsController.request(permissions)
            latestResult.value = result
        }
    }
}