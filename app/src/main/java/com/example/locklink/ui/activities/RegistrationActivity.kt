package com.example.locklink.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.locklink.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Date
import com.example.locklink.models.data.User

class RegistrationActivity : AppCompatActivity() {
    private lateinit var fullName: TextInputEditText
    private lateinit var phoneNumber: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var confirmPassword: TextInputEditText
    private lateinit var submitButton: MaterialButton
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var selectedImg: Uri
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.hide()

        auth = Firebase.auth

        fullName = findViewById(R.id.fullNamePerson)
        phoneNumber = findViewById(R.id.phoneNumberPerson)
        email = findViewById(R.id.emailPerson)
        password = findViewById(R.id.passwordPerson)
        confirmPassword = findViewById(R.id.confirmPasswordPerson)

        submitButton = findViewById(R.id.signUp)
        progressBar = findViewById(R.id.progressBar)

        submitButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            val emailForReg = email.text.toString()
            val passwordForReg = password.text.toString()

            auth.createUserWithEmailAndPassword(emailForReg, passwordForReg)
                .addOnCompleteListener() { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Account created.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }

            uploadData()
        }

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        val img = findViewById<CircleImageView>(R.id.personImage).setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                selectedImg = data.data!!
                val img = findViewById<CircleImageView>(R.id.personImage)
                img?.setImageURI(selectedImg)
            }

        }
    }

    private fun uploadData() {
        val reference = storage.reference.child("Profile").child(Date().time.toString())
        reference.putFile(selectedImg).addOnCompleteListener {
            if (it.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener { task ->
                    uploadInfo(task.toString())
                }

            }
        }
    }

    private fun uploadInfo(imgUrl: String) {
        val user =
            User(auth.uid.toString(), fullName.text.toString(), phoneNumber.text.toString(), imgUrl)

        database.reference.child("Users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

    }
}