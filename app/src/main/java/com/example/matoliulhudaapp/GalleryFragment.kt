package com.example.matoliulhudaapp.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matoliulhudaapp.ApiClient
import com.example.matoliulhudaapp.GalleryImage
import com.example.matoliulhudaapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)

        recyclerView = view.findViewById(R.id.rvGallery)

        // Menggunakan GridLayoutManager dengan 2 kolom
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        loadGallery()

        return view
    }

    private fun loadGallery() {
        ApiClient.instance.getGallery().enqueue(object : Callback<List<GalleryImage>> {
            override fun onResponse(
                call: Call<List<GalleryImage>>,
                response: Response<List<GalleryImage>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    recyclerView.adapter = GalleryAdapter(data)
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat galeri", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<GalleryImage>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}