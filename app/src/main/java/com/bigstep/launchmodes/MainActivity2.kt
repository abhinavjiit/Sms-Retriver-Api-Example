package com.bigstep.launchmodes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Log.d("MainActivity2", "onCreate()")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("MainActivity2", "onNewIntent()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity2", "onStart()")

    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity2", "onRestart()")

    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity2", "onResume()")

    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity2", "onPause()")

    }


    override fun onStop() {
        super.onStop()
        Log.d("MainActivity2", "onStop()")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity2", "onDestroy()")

    }
}