package com.example.matoliulhudaapp
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("berita")
    fun getBerita(): Call<List<News>>
}
