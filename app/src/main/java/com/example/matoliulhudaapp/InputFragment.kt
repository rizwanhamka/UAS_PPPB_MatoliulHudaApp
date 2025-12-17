package com.example.matoliulhudaapp.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matoliulhudaapp.ApiClient
import com.example.matoliulhudaapp.FeedbackRequest
import com.example.matoliulhudaapp.FeedbackResponse
import com.example.matoliulhudaapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputFragment : Fragment() {

    private lateinit var etNama: EditText
    private lateinit var etPesan: EditText
    private lateinit var btnKirim: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Inisialisasi View
        etNama = view.findViewById(R.id.inputNama)
        etPesan = view.findViewById(R.id.inputPesan)
        btnKirim = view.findViewById(R.id.btnKirim)

        // 2. Event Klik Tombol Kirim
        btnKirim.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val pesan = etPesan.text.toString().trim()

            // Validasi Input tidak boleh kosong
            if (nama.isEmpty()) {
                etNama.error = "Nama harus diisi"
                return@setOnClickListener
            }
            if (pesan.isEmpty()) {
                etPesan.error = "Pesan masukkan harus diisi"
                return@setOnClickListener
            }

            // Jika valid, kirim ke server
            postDataKeServer(nama, pesan)
        }
    }

    private fun postDataKeServer(nama: String, pesan: String) {
        // Matikan tombol agar tidak diklik berkali-kali saat loading
        btnKirim.isEnabled = false
        btnKirim.text = "Mengirim..."

        // Siapkan data request
        val requestData = FeedbackRequest(nama, pesan)

        // Panggil API
        ApiClient.instance.kirimMasukkan(requestData).enqueue(object : Callback<FeedbackResponse> {
            override fun onResponse(
                call: Call<FeedbackResponse>,
                response: Response<FeedbackResponse>
            ) {
                // Hidupkan tombol kembali
                btnKirim.isEnabled = true
                btnKirim.text = "Kirim"

                if (response.isSuccessful) {
                    // Sukses
                    val resp = response.body()
                    Toast.makeText(requireContext(), resp?.message ?: "Berhasil dikirim!", Toast.LENGTH_LONG).show()

                    // Kosongkan form setelah sukses
                    etNama.text.clear()
                    etPesan.text.clear()
                } else {
                    // Gagal dari server (misal error 400/500)
                    Toast.makeText(requireContext(), "Gagal: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FeedbackResponse>, t: Throwable) {
                // Error koneksi / internet mati
                btnKirim.isEnabled = true
                btnKirim.text = "Kirim"

                t.printStackTrace()
                Toast.makeText(requireContext(), "Error koneksi: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}