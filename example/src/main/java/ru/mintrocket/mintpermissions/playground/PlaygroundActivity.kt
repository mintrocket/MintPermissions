package ru.mintrocket.mintpermissions.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.mintrocket.lib.mintpermissions.MintPermissionsManager
import ru.mintrocket.mintpermissions.R

class PlaygroundActivity : AppCompatActivity(R.layout.activity_playground) {

    private val permissionManager by inject<MintPermissionsManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager.init(this)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, PlaygroundFragment())
                .commitNow()
        }
    }
}