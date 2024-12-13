package com.aruel.checkupapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckupDao {
    @Insert
    suspend fun insertHistory(history: CheckupHistory)

    @Query("SELECT * FROM checkup_history ORDER BY id DESC")
    fun getAllHistory(): Flow<List<CheckupHistory>>
}