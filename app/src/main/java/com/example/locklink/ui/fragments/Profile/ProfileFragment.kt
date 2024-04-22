package com.example.locklink.ui.fragments.Profile

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.locklink.R
import com.example.locklink.models.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import io.grpc.Context.Storage
import java.io.File

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbReference: DatabaseReference
    private lateinit var stReference: StorageReference
    private lateinit var user: User
    private lateinit var uid: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()


        dbReference = FirebaseDatabase.getInstance().getReference("Profile")
        if (uid.isNotEmpty()) {
            getUserData()
        }
    }

    private fun getUserData() {
        dbReference.child(uid).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!

                val name = view?.findViewById<TextView>(R.id.personNameContent)
                name?.setText(user.name)

                val email = view?.findViewById<TextView>(R.id.personEmailContent)
                email?.setText(user.email)

                getUserProfile()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        } )
    }

    private fun getUserProfile() {
        stReference = FirebaseStorage.getInstance().reference.child("Profile/$uid.jpg")
        val localFile = File.createTempFile("tempImage", "jpg")
        stReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
             val img = view?.findViewById<CircleImageView>(R.id.personImageContent)
            img?.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }


    }
}