package com.example.matoliulhudaapp.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matoliulhudaapp.News
import com.example.matoliulhudaapp.R

class NewsAdapter(
    private val list: List<News>,
    private val onReadMoreClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val desc: TextView = view.findViewById(R.id.tvDesc)
        val date: TextView = view.findViewById(R.id.tvDate)
        val img: ImageView = view.findViewById(R.id.imgNews)
        val btn: Button = view.findViewById(R.id.btnReadMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = list[position]

        holder.title.text = item.title ?: "-"
        holder.desc.text = item.description ?: "-"
        holder.date.text = item.date?.substring(0, 10) ?: "-"

        Glide.with(holder.img.context)
            .load(item.imageUrl)
            .into(holder.img)

        holder.btn.setOnClickListener {
            onReadMoreClick(item)
        }
    }

    override fun getItemCount(): Int = list.size
}
