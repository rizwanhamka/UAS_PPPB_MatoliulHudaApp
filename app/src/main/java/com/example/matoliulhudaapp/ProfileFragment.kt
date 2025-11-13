package com.example.matoliulhudaapp.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matoliulhudaapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class ProfileFragment : Fragment() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val nameView = view.findViewById<TextView>(R.id.nameText)
        val emailView = view.findViewById<TextView>(R.id.emailText)
        val editButton = view.findViewById<Button>(R.id.editButton)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        val uid = auth.currentUser?.uid ?: return view
        db.child(uid).get().addOnSuccessListener {
            val name = it.child("name").value.toString()
            val email = it.child("email").value.toString()
            nameView.text = name
            emailView.text = email
        }

        editButton.setOnClickListener {
            val newName = "Nama Baru" // (buat dialog edit)
            db.child(uid).child("name").setValue(newName)
            Toast.makeText(requireContext(), "Profil diperbarui", Toast.LENGTH_SHORT).show()
        }

        logoutButton.setOnClickListener {
            auth.signOut()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        }

        return view
    }
}