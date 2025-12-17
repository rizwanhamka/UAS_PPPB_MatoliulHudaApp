package com.example.matoliulhudaapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matoliulhudaapp.GalleryImage
import com.example.matoliulhudaapp.R

// Ubah 'val items' menjadi 'var items' agar bisa diubah
class ImageSliderAdapter(private var items: List<GalleryImage>) :
    RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgSlider)

        fun bind(image: GalleryImage) {
            Glide.with(itemView.context)
                .load(image.url)
                .centerCrop()
                .placeholder(R.color.gray) // Pastikan warna ini ada di colors.xml
                .error(android.R.drawable.ic_menu_report_image) // Icon jika gagal load
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    // TAMBAHKAN FUNGSI INI
    fun updateData(newItems: List<GalleryImage>) {
        items = newItems
        notifyDataSetChanged()
    }
}