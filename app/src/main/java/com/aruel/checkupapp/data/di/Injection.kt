package com.aruel.checkupapp.data.di

import android.content.Context
import com.aruel.checkupapp.data.pref.UserPreference
import com.aruel.checkupapp.data.pref.dataStore
import com.aruel.checkupapp.data.repository.UserRepository
import com.aruel.checkupapp.data.retrofit.ApiConfig
import com.aruel.checkupapp.data.retrofit.ApiConfigNews
import com.aruel.checkupapp.data.retrofit.ApiConfigPredictions
import com.aruel.checkupapp.data.room.CheckupDatabase

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val database = CheckupDatabase.getInstance(context)
        val checkupHistoryDao = database.checkupDao()
        val apiServicePrediction = ApiConfigPredictions.getApiService()
        val apiServiceNews = ApiConfigNews.getApiService()
        return UserRepository.getInstance(pref,checkupHistoryDao, apiService,apiServicePrediction,apiServiceNews)
    }
}