package com.bigstep.launchmodes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main4.*

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        text4.setOnClickListener {

            val int = Intent(this, MainActivity5::class.java)
            startActivity(int)
            finish()
        }
    }
}