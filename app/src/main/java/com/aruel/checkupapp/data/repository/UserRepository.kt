package com.aruel.checkupapp.data.repository

import androidx.lifecycle.LiveData
import com.aruel.checkupapp.data.pref.UserModel
import com.aruel.checkupapp.data.pref.UserPreference
import com.aruel.checkupapp.data.response.ArticleResponse
import com.aruel.checkupapp.data.response.ArticlesItem
import com.aruel.checkupapp.data.response.CheckUpResponse
import com.aruel.checkupapp.data.response.LoginResponse
import com.aruel.checkupapp.data.response.NewsResponse
import com.aruel.checkupapp.data.response.RegisterResponse
import com.aruel.checkupapp.data.retrofit.ApiService
import com.aruel.checkupapp.data.retrofit.ApiServiceNews
import com.aruel.checkupapp.data.retrofit.ApiServicePredictions
import com.aruel.checkupapp.data.room.CheckupDao
import com.aruel.checkupapp.data.room.CheckupHistory
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userPreference: UserPreference,
    private val checkupHistoryDao: CheckupDao,
    private val apiService: ApiService,
    private val apiServicePrediction: ApiServicePredictions,
    private val apiServiceNews: ApiServiceNews
)
{
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun logout() {
        saveSession(UserModel(email = "", token = "", isLogin = false))
        userPreference.logout()
    }

    suspend fun submitPrediction(request: ApiServicePredictions.CheckUpRequest): CheckUpResponse {
        return apiServicePrediction.submitCheckup(request)
    }

    suspend fun getHealthNews(): NewsResponse {
        return apiServiceNews.getHealthNews()
    }

    suspend fun getArticles(result: String): ArticleResponse {
        return apiServicePrediction.getArticles(ApiServicePredictions.PredictionResult(result))
    }

    suspend fun saveCheckupHistory(history: CheckupHistory) {
        checkupHistoryDao.insertHistory(history)
    }

    fun getCheckupHistory(): Flow<List<CheckupHistory>> {
        return checkupHistoryDao.getAllHistory()
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            checkupHistoryDao: CheckupDao,
            apiService: ApiService,
            apiServicePrediction: ApiServicePredictions,
            apiServiceNews: ApiServiceNews
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, checkupHistoryDao, apiService,apiServicePrediction,apiServiceNews)
            }.also { instance = it }
    }

}