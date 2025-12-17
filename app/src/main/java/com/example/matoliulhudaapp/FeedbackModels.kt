package com.example.matoliulhudaapp

// Data yang akan dikirim ke Server
data class FeedbackRequest(
    val nama: String,
    val masukkan: String
)

// Data respon dari Server (sesuai backend: { message: "...", data: ... })
data class FeedbackResponse(
    val message: String
)