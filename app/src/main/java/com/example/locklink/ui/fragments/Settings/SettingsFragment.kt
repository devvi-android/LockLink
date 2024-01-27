package com.example.locklink.ui.fragments.Settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.locklink.R
import com.example.locklink.ui.activities.LoginActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class SettingsFragment : Fragment() {
    private lateinit var invite: MaterialButton
    private lateinit var logOut: MaterialButton

    private lateinit var googleAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logOut = view.findViewById(R.id.logOut)
        invite = view.findViewById(R.id.invite)

        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        googleAuth = FirebaseAuth.getInstance()

        logOut.setOnClickListener {
            MaterialAlertDialogBuilder(
                it.context
            )
                .setTitle("LogOut")
                .setMessage(R.string.message)
                .setNegativeButton(R.string.no_btn) { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton(R.string.yes_btn) { dialog, which ->
                    dialog.dismiss()
                    googleAuth.signOut()
                    startActivity(Intent(activity, LoginActivity::class.java))

                }
                .show()
        }

        invite.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody =
                getString(R.string.share_content)
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

    }
}