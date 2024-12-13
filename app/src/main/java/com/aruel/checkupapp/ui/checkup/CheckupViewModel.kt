package com.aruel.checkupapp.ui.checkup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruel.checkupapp.data.repository.UserRepository
import com.aruel.checkupapp.data.response.CheckUpResponse
import com.aruel.checkupapp.data.retrofit.ApiServicePredictions
import com.aruel.checkupapp.data.room.CheckupHistory
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckupViewModel(private val repository: UserRepository) : ViewModel() {

    private val _checkUpResult = MutableLiveData<CheckUpResponse?>()
    val checkUpResult: LiveData<CheckUpResponse?> = _checkUpResult

    fun submitCheckup(answers: List<String>, onSuccess: (CheckUpResponse) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val request = ApiServicePredictions.CheckUpRequest(answers)
                val response = repository.submitPrediction(request)


                onSuccess(response)
            } catch (e: Exception) {
                onError(e.message ?: "Terjadi kesalahan.")
            }
        }
    }
    fun mapResponseToHistory(response: CheckUpResponse): CheckupHistory {
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate = dateFormatter.format(Date(System.currentTimeMillis()))
        return CheckupHistory(
            date = formattedDate,
            predictedClass = response.predictedClass ?: "N/A",
            suggestion = response.suggestion ?: "N/A",
            definition = response.definition ?: "N/A",
            category = response.category ?: "N/A",
            severity = response.severity ?: "N/A"
        )
    }


    fun saveHistory(history: CheckupHistory) {
        viewModelScope.launch {
            repository.saveCheckupHistory(history)
        }
    }


}
