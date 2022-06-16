package ru.mintrocket.mintpermissions.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.mintrocket.mintpermissions.R

class PlaygroundActivity : AppCompatActivity(R.layout.activity_playground) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, PlaygroundFragment())
                .commitNow()
        }
    }
}