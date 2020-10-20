package com.bigstep.launchmodes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main5.*

class MainActivity5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        text5.setOnClickListener {

            val int = Intent(this, MainActivity4::class.java)
            startActivity(int)
            finish()

        }
    }
}