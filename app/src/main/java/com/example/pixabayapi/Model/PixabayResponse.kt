package com.example.pixabayapi.Model

data class PixabayResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<Image>
)