package com.example.matoliulhudaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matoliulhudaapp.components.ContactFragment
import com.example.matoliulhudaapp.components.GalleryFragment
import com.example.matoliulhudaapp.components.InputFragment
import com.example.matoliulhudaapp.components.LoginFragment
import com.example.matoliulhudaapp.components.NewsFragment
import com.example.matoliulhudaapp.components.PengurusFragment
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Definisi Tombol Menu
        val btnBerita = view.findViewById<LinearLayout>(R.id.menuBerita)
        val btnGallery = view.findViewById<LinearLayout>(R.id.menuGallery)
        val btnInput = view.findViewById<LinearLayout>(R.id.menuInput)
        val btnPengurus = view.findViewById<LinearLayout>(R.id.menuPengurus)
        val btnContact = view.findViewById<LinearLayout>(R.id.menuContact)
        val btnLogout = view.findViewById<LinearLayout>(R.id.menuLogout)

        // 2. Logic Klik Berita -> Pindah ke NewsFragment
        btnBerita.setOnClickListener {
            loadFragment(NewsFragment())
        }

        // 3. Logic Klik Gallery -> Pindah ke GalleryFragment
        btnGallery.setOnClickListener {
            loadFragment(GalleryFragment())
        }

        // 4. Logic Klik Masukkan -> Pindah ke InputFragment
        btnInput.setOnClickListener {
            loadFragment(InputFragment())
        }

        btnPengurus.setOnClickListener {
            loadFragment(PengurusFragment())
        }

        btnContact.setOnClickListener {
            loadFragment(ContactFragment())
        }

        btnLogout?.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show()

            // Pindah ke Login
            loadFragment(LoginFragment())
        }
    }

    // Fungsi Helper untuk ganti fragment
    private fun loadFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment) // Pastikan ID ini sama dengan yang di MainActivity
            .addToBackStack(null) // Agar bisa di-back kembali ke Home
            .commit()
    }
}