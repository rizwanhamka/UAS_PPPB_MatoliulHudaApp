package com.example.matoliulhudaapp.components

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.matoliulhudaapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    // Pastikan referensi ini sesuai dengan tempat Anda menyimpan data.
    // Berdasarkan kode Register sebelumnya, kita menyimpannya di folder "users"
    private val db by lazy { FirebaseDatabase.getInstance().getReference("users") }

    private var nameView: TextView? = null
    private var emailView: TextView? = null

    // Variabel untuk listener agar bisa dicopot saat fragment hancur (mencegah crash)
    private var profileListener: ValueEventListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            // 1. Binding View
            nameView = view.findViewById(R.id.nameText)
            emailView = view.findViewById(R.id.emailText)
            val editButton = view.findViewById<Button>(R.id.editButton)
            val logoutButton = view.findViewById<Button>(R.id.logoutButton)

            // 2. Cek User Login
            val user = auth.currentUser
            if (user == null) {
                goToLogin()
                return
            }

            val uid = user.uid

            // Set tampilan awal (Loading)
            nameView?.text = "Mengambil data..."
            emailView?.text = user.email // Email dari Auth (Backup)

            // 3. Ambil Data Realtime (Menggunakan ValueEventListener)
            // Ini akan otomatis terpanggil jika ada perubahan data di Firebase
            profileListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Cek jika fragment sudah ditutup agar tidak crash
                    if (!isAdded || context == null) return

                    if (snapshot.exists()) {
                        // Data ditemukan! Ambil sesuai nama field di JSON Anda
                        val name = snapshot.child("name").value?.toString()
                        val email = snapshot.child("email").value?.toString()

                        // Update UI
                        nameView?.text = if (!name.isNullOrEmpty()) name else "Nama belum diatur"
                        emailView?.text = if (!email.isNullOrEmpty()) email else user.email

                    } else {
                        // Data kosong di database
                        nameView?.text = "Data Kosong"
                        Toast.makeText(context, "Data user tidak ditemukan di database", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    if (isAdded) {
                        Toast.makeText(context, "Error Database: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Pasang listener ke path: users/UID
            db.child(uid).addValueEventListener(profileListener!!)

            // 4. Tombol Edit
            editButton.setOnClickListener {
                showEditDialog(uid)
            }

            // 5. Tombol Logout
            logoutButton.setOnClickListener {
                auth.signOut()
                goToLogin()
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error Tampilan: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEditDialog(uid: String) {
        if (context == null) return

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Edit Profil")

        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val inputName = EditText(requireContext())
        inputName.hint = "Nama Lengkap"
        inputName.setText(nameView?.text)
        layout.addView(inputName)

        builder.setView(layout)

        builder.setPositiveButton("Simpan") { _, _ ->
            val newName = inputName.text.toString().trim()
            if (newName.isNotEmpty()) {
                // Update hanya field 'name'
                db.child(uid).child("name").setValue(newName)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Nama berhasil diubah!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Gagal update: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun goToLogin() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, LoginFragment())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Copot listener saat pindah halaman agar aplikasi tidak berat/crash
        val user = auth.currentUser
        if (user != null && profileListener != null) {
            db.child(user.uid).removeEventListener(profileListener!!)
        }
        nameView = null
        emailView = null
    }
}