package com.example.matoliulhudaapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val id: Long, // <-- FIX
    val title: String,
    val description: String,
    @SerializedName("image") val imageUrl: String,
    val date: String
) : Parcelable
