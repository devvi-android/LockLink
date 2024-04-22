package com.example.locklink.ui.fragments.QR

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.locklink.R
import com.google.android.material.button.MaterialButton
import com.google.zxing.integration.android.IntentIntegrator

class QrFragment : Fragment() {
    val scanBtn = view?.findViewById<MaterialButton>(R.id.qrGenerate)
    val qrInfo = view?.findViewById<TextView>(R.id.qrInfo)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scanBtn?.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this.activity)
            intentIntegrator.setOrientationLocked(true)
            intentIntegrator.setPrompt("Scan a QR code")
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode, data)
        if (intentResult != null) {
            val contents = intentResult.contents

            if (contents != null) {
                qrInfo?.text = intentResult.contents
            }
        }
    }
}