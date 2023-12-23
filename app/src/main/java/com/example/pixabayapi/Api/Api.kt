package com.example.pixabayapi.Api

import com.example.pixabayapi.Model.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/api/")
    suspend fun getImages(
        @Query("key") apiKey: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("q") searchText: String
    ): PixabayResponse
}