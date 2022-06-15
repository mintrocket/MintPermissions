package ru.mintrocket.mintpermissions.data_flow

import android.Manifest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterNotGranted
import ru.mintrocket.lib.mintpermissions.ext.isAllGranted

class DataFlowRepository(
    private val permissionsController: MintPermissionsController
) {

    suspend fun searchNearestShops(): List<String> {
        return withContext(Dispatchers.IO) {
            val someId = getSomeId()
            val location = getLocation(someId)
            apiSearchNearestShops(location)
        }
    }

    private suspend fun getSomeId(): Long {
        delay((100..500).random().toLong())
        return (0..1000).random().toLong()
    }

    private suspend fun getLocation(someId: Long): String {
        val locationPermissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        val result = permissionsController.request(locationPermissions).results
        require(result.isAllGranted()) {
            "Has not granted permissions ${result.filterNotGranted().map { it.status }}"
        }
        //simulate fetch location
        delay((100..5000).random().toLong())
        return "location for id $someId"
    }

    private suspend fun apiSearchNearestShops(location: String): List<String> {
        delay((100..500).random().toLong())
        return (0 until 10).map {
            "Shop ${it + 1} at $location"
        }
    }
}