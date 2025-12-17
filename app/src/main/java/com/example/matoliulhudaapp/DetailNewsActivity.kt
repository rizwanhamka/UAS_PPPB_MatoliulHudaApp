package com.example.matoliulhudaapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        // Inisialisasi View
        val imgDetail: ImageView = findViewById(R.id.imgDetail)
        val tvTitle: TextView = findViewById(R.id.tvDetailTitle)
        val tvDate: TextView = findViewById(R.id.tvDetailDate)
        val tvDesc: TextView = findViewById(R.id.tvDetailDesc)

        // Ambil data dari Intent
        val news = intent.getParcelableExtra<News>("EXTRA_NEWS")

        // Tampilkan data jika ada
        if (news != null) {
            tvTitle.text = news.title
            tvDate.text = news.date
            tvDesc.text = news.description

            Glide.with(this)
                .load(news.imageUrl)
                .into(imgDetail)
        }

        // Opsional: Menambahkan tombol back di Action Bar
        supportActionBar?.title = "Detail Berita"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // Aksi ketika tombol back di pojok kiri atas ditekan
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}