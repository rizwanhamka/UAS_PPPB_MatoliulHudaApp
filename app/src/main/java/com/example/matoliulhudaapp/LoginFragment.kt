package com.example.matoliulhudaapp.components

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.matoliulhudaapp.R
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        auth = FirebaseAuth.getInstance()

        val emailInput = view.findViewById<EditText>(R.id.emailInput)
        val passwordInput = view.findViewById<EditText>(R.id.passwordInput)
        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val registerLink = view.findViewById<TextView>(R.id.registerLink)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, ProfileFragment())
                        .commit()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Gagal login: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        registerLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RegisterFragment())
                .commit()
        }

        return view
    }
}
