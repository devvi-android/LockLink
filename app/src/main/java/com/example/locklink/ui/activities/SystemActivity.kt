package com.example.locklink.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.example.locklink.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class SystemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system)
        val qr = findViewById<ImageView>(R.id.qr)
        val qrEmail = findViewById<TextInputEditText>(R.id.qrEmail)
        val qrBtn = findViewById<MaterialButton>(R.id.qrGenerate)

        qrBtn.setOnClickListener(View.OnClickListener {
            val text = qrEmail.text.toString().trim()
            val writer = MultiFormatWriter()

                val matrix = writer.encode(text, BarcodeFormat.QR_CODE, 350, 350)
                val encoder = BarcodeEncoder()
                val bitmap = encoder.createBitmap(matrix)

                qr.setImageBitmap(bitmap)

        })
    }
}