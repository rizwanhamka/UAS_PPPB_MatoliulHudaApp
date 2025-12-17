package com.example.matoliulhudaapp.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.matoliulhudaapp.News
import com.example.matoliulhudaapp.R

class DetailNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgDetail: ImageView = view.findViewById(R.id.imgDetail)
        val tvTitle: TextView = view.findViewById(R.id.tvDetailTitle)
        val tvDate: TextView = view.findViewById(R.id.tvDetailDate)
        val tvDesc: TextView = view.findViewById(R.id.tvDetailDesc)

        // Menerima data dari NewsFragment
        val news = arguments?.getParcelable<News>("EXTRA_NEWS")

        if (news != null) {
            tvTitle.text = news.title
            tvDate.text = news.date
            tvDesc.text = news.description

            Glide.with(this)
                .load(news.imageUrl)
                .into(imgDetail)
        }
    }
}