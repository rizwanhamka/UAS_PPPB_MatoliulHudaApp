package com.example.matoliulhudaapp.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matoliulhudaapp.GalleryImage
import com.example.matoliulhudaapp.R
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast


class GalleryAdapter(
    private val list: List<GalleryImage>,
    private val context: Context
) :
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

        Glide.with(holder.itemView.context)
            .load(item.url)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.img)

        holder.img.setOnClickListener {
            downloadImage(item.url)
        }
    }

    private fun downloadImage(url: String) {
        try {
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("Download Gambar")
                .setDescription("Mengunduh gambar galeri")
                .setNotificationVisibility(
                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                )
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    "gallery_${System.currentTimeMillis()}.jpg"
                )

            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)

            Toast.makeText(context, "Download dimulai", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(context, "Gagal mengunduh gambar", Toast.LENGTH_SHORT).show()
        }
    }



    override fun getItemCount(): Int = list.size
}