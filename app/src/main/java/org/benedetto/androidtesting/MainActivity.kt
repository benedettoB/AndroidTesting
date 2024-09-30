package org.benedetto.androidtesting

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import org.benedetto.androidtesting.util.log

class MainActivity : ComponentActivity() {

     private val TAG = "MainActivity"

    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        button.setOnClickListener { Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show() }
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