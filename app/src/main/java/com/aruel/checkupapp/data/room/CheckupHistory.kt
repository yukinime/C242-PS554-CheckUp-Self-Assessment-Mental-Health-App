package com.aruel.checkupapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checkup_history")
data class CheckupHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val predictedClass: String,
    val suggestion: String,
    val severity: String,
    val category: String,
    val definition : String,

)
