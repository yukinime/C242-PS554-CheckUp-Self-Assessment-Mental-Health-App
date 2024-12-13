package com.aruel.checkupapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruel.checkupapp.data.pref.UserModel
import com.aruel.checkupapp.data.repository.UserRepository
import com.aruel.checkupapp.data.response.ArticlesItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel (private val repository: UserRepository) : ViewModel()  {

    private val _news = MutableStateFlow<List<ArticlesItem>>(emptyList())
    val news: StateFlow<List<ArticlesItem>> get() = _news


    fun fetchHealthNews() {
        viewModelScope.launch {
            val response = repository.getHealthNews()
            _news.value = response.articles
        }
    }

    fun getSession(): Flow<UserModel> {
        return repository.getSession()
    }

}