package com.rezzavinola.dogapplication.data.remote

import com.rezzavinola.dogapplication.data.model.response.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("images/search")
    suspend fun searchImages(
        @Query("size") size: String? = null, // thumb , small, med, full
        @Query("mime_types") mimeType: String? = null, // jpg, png, gif
        @Query("format") format: String? = null, // json, src
        @Query("order") order: String? = null, // RANDOM, ASC, DESC
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null, // 1 - 25
        @Query("has_breeds") hasBreeds: Boolean? = null
    ): Response<SearchResponse>
}
