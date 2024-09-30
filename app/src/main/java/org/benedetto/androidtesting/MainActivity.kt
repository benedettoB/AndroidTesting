package org.benedetto.androidtesting

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import org.benedetto.androidtesting.util.log

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            //Show a dialog
            AlertDialog.Builder(this)
                .setTitle("Test Dialog")
                .setMessage("This is a dialog")
                .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        log(TAG, "onStart() fired")
    }

    override fun onResume() {
        super.onResume()
        log(TAG, "onResume() fired")
    }

    override fun onPause() {
        super.onPause()
        log(TAG, "onPause() fired")
    }

    override fun onStop() {
        super.onStop()
        log(TAG, "onStop() fired")
    }

    override fun onDestroy() {
        super.onDestroy()
        log(TAG, "onDestory() fired")
    }
}