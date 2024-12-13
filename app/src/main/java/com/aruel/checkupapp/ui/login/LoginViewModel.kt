package com.aruel.checkupapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruel.checkupapp.data.pref.UserModel
import com.aruel.checkupapp.data.repository.UserRepository
import com.aruel.checkupapp.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user.copy(isLogin = true))        }
    }
    suspend fun login(email: String, password: String): LoginResponse {
        return repository.login(email, password)
    }
}