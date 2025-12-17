package com.example.matoliulhudaapp.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.matoliulhudaapp.Pengurus
import com.example.matoliulhudaapp.R

class PengurusAdapter(private val list: List<Pengurus>) : RecyclerView.Adapter<PengurusAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgPengurus)
        val nama: TextView = view.findViewById(R.id.tvNama)
        val jabatan: TextView = view.findViewById(R.id.tvJabatan)
        val hp: TextView = view.findViewById(R.id.tvNoHp)
        val email: TextView = view.findViewById(R.id.tvEmail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pengurus, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.nama.text = item.nama
        holder.jabatan.text = item.jabatan
        holder.hp.text = item.noHp
        holder.email.text = item.email

        // Menggunakan gambar dari drawable local
        holder.img.setImageResource(item.imageResId)

        // Jika ingin pakai foto pengurus sungguhan, ganti item.imageResId dengan URL dan pakai Glide
    }

    override fun getItemCount(): Int = list.size
}