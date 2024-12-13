package com.aruel.checkupapp.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aruel.checkupapp.data.repository.UserRepository

class ResultViewModel (private val repository: UserRepository) : ViewModel() {

    private val _results = MutableLiveData<List<String>>()
    val results: LiveData<List<String>> get() = _results

    fun setResults(answers: List<String>) {
        _results.value = answers
    }
}