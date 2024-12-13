package com.aruel.checkupapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aruel.checkupapp.data.repository.UserRepository
import com.aruel.checkupapp.data.room.CheckupHistory
import kotlinx.coroutines.flow.Flow

class HistoryViewModel(private val repository: UserRepository) : ViewModel() {
    fun getHistory(): Flow<List<CheckupHistory>> {
        return repository.getCheckupHistory()
    }
}