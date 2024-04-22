package com.example.locklink.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.locklink.R
import com.google.android.material.button.MaterialButton

class ChooseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        val system = findViewById<MaterialButton>(R.id.system)
        val employee = findViewById<MaterialButton>(R.id.employee)

        system.setOnClickListener {
            val intent = Intent(this, SystemLoginActivity::class.java)
            startActivity(intent)
        }

        employee.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}