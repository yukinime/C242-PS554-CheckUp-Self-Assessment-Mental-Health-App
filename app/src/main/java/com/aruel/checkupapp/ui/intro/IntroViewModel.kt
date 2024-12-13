package com.aruel.checkupapp.ui.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aruel.checkupapp.data.pref.UserModel
import com.aruel.checkupapp.data.repository.UserRepository

class IntroViewModel(private val repository: UserRepository) : ViewModel()  {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}