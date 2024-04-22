package com.example.locklink.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.locklink.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SystemLoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_login)
        supportActionBar?.hide()
        auth = Firebase.auth

        val resetPasswordBtn = findViewById<Button>(R.id.resetPasswordBtn)
        val signIn = findViewById<Button>(R.id.signIn)
        val email = findViewById<TextInputEditText>(R.id.admin)
        val password = findViewById<TextInputEditText>(R.id.adminPassword)
        progressBar = findViewById(R.id.progressBar)

        resetPasswordBtn.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        signIn.setOnClickListener {
            if (email.text.isNullOrBlank()) {
                Toast.makeText(
                    this,
                    "Enter email",
                    Toast.LENGTH_SHORT,
                ).show()
            }

            if (password.text.isNullOrBlank()) {
                Toast.makeText(
                    this,
                    "Enter password",
                    Toast.LENGTH_SHORT,
                ).show()
            }

            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE

                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "Login Successful.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        val intent = Intent(this, SystemActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}