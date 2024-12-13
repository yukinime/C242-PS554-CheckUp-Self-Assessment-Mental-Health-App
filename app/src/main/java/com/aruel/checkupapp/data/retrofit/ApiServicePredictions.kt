package com.aruel.checkupapp.data.retrofit

import com.aruel.checkupapp.data.response.ArticleResponse
import com.aruel.checkupapp.data.response.CheckUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServicePredictions {

    data class CheckUpRequest(val answers: List<String>)
    @POST("predict")
    suspend fun submitCheckup(@Body request: CheckUpRequest): CheckUpResponse

    data class PredictionResult(val result: String)

    @POST("api/articles")
    suspend fun getArticles(@Body result: PredictionResult): ArticleResponse
}