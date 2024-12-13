package com.aruel.checkupapp.data.retrofit

import com.aruel.checkupapp.data.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceNews {
    @GET("top-headlines")
    suspend fun getHealthNews(
        @Query("q") query: String = "cancer",
        @Query("category") category: String = "health",
        @Query("language") language: String = "en",
        @Query("apiKey") apiKey: String = "5e9fea045d874d28925eaf39993b781d"
    ): NewsResponse
}