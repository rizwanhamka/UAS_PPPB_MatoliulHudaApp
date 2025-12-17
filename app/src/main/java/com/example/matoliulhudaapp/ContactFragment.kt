package com.example.matoliulhudaapp.components

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matoliulhudaapp.R

class ContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnHotline = view.findViewById<LinearLayout>(R.id.btnHotline)
        val btnIg = view.findViewById<LinearLayout>(R.id.btnInstagram)
        val btnFb = view.findViewById<LinearLayout>(R.id.btnFacebook)
        val btnEmail = view.findViewById<LinearLayout>(R.id.btnEmail)

        // 1. Action Call / WhatsApp
        btnHotline.setOnClickListener {
            // Menggunakan ACTION_DIAL agar user menekan tombol panggil sendiri (lebih aman)
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:081234567890") // Ganti dengan nomor asli
            startActivity(intent)
        }

        // 2. Action Buka Instagram
        btnIg.setOnClickListener {
            val url = "https://www.instagram.com/matoliulhuda_official" // Ganti link asli
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // 3. Action Buka Facebook
        btnFb.setOnClickListener {
            val url = "https://www.facebook.com/matoliulhuda" // Ganti link asli
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // 4. Action Kirim Email
        btnEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:admin@matoliulhuda.com") // Ganti email asli
            intent.putExtra(Intent.EXTRA_SUBJECT, "Tanya Info Yayasan")

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Tidak ada aplikasi email terinstall", Toast.LENGTH_SHORT).show()
            }
        }
    }
}