package com.example.matoliulhudaapp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("berita")
    fun getBerita(): Call<List<News>>

    @GET("gallery")
    fun getGallery(): Call<List<GalleryImage>>

    @POST("masukkan")
    fun kirimMasukkan(@Body request: FeedbackRequest): Call<FeedbackResponse>
}
