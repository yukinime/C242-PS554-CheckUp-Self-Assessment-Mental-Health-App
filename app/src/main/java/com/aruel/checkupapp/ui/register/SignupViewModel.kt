package com.aruel.checkupapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruel.checkupapp.data.repository.UserRepository
import com.aruel.checkupapp.data.response.RegisterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignupViewModel(private val repository: UserRepository) : ViewModel() {

    private val _registerResult = MutableStateFlow<RegisterResponse?>(null)
    val registerResult: StateFlow<RegisterResponse?> = _registerResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.registerUser(name, email, password)
                _registerResult.value = response
            } catch (e: Exception) {
                _registerResult.value = RegisterResponse(success = false, message = e.message,userId = null )
            }
        }
    }
}