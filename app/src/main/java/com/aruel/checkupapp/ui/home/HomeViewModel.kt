package com.aruel.checkupapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruel.checkupapp.data.repository.UserRepository
import com.aruel.checkupapp.data.response.ArticleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository) : ViewModel() {

    private val _articles = MutableStateFlow<ArticleResponse?>(null)
    val articles: StateFlow<ArticleResponse?> get() = _articles

    fun fetchArticles(result: String) {
        viewModelScope.launch {
            val response = repository.getArticles(result)
            _articles.value = response
        }
    }
}