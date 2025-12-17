package com.example.matoliulhudaapp.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matoliulhudaapp.GalleryImage
import com.example.matoliulhudaapp.R

class GalleryAdapter(private val list: List<GalleryImage>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    class GalleryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgGallery)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = list[position]

        // Load gambar menggunakan Glide
        Glide.with(holder.itemView.context)
            .load(item.url)
            .placeholder(R.drawable.ic_launcher_background) // Gambar sementara saat loading
            .into(holder.img)
    }

    override fun getItemCount(): Int = list.size
}