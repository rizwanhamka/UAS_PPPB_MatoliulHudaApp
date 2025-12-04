package com.example.matoliulhudaapp

import com.google.gson.annotations.SerializedName

data class News(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("image") val imageUrl: String,
    val date: String
)
