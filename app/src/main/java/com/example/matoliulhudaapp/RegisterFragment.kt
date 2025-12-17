package com.example.matoliulhudaapp.components

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.matoliulhudaapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        auth = FirebaseAuth.getInstance()

        val nameInput = view.findViewById<EditText>(R.id.nameInput)
        val emailInput = view.findViewById<EditText>(R.id.emailInput)
        val passwordInput = view.findViewById<EditText>(R.id.passwordInput)
        val registerButton = view.findViewById<Button>(R.id.registerButton)
        val loginLink = view.findViewById<TextView>(R.id.loginLink)

        registerButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.length < 6) {
                Toast.makeText(requireContext(), "Isi semua data. Password min 6 karakter.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerButton.isEnabled = false
            registerButton.text = "Mendaftarkan..."

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val uid = auth.currentUser?.uid ?: return@addOnSuccessListener
                    val userData = mapOf(
                        "uid" to uid,
                        "name" to name,
                        "email" to email,
                        "role" to "user"
                    )

                    db.child(uid).setValue(userData)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, LoginFragment())
                                .commit()
                        }
                }
                .addOnFailureListener { e ->
                    registerButton.isEnabled = true
                    registerButton.text = "DAFTAR"
                    Toast.makeText(requireContext(), "Gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        loginLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        }

        return view
    }
}