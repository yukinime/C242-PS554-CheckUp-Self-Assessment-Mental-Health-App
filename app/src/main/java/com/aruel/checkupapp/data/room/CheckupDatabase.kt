package com.aruel.checkupapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CheckupHistory::class], version = 1, exportSchema = false)
abstract class CheckupDatabase : RoomDatabase() {

    abstract fun checkupDao(): CheckupDao

    companion object {
        @Volatile
        private var INSTANCE: CheckupDatabase? = null

        fun getInstance(context: Context): CheckupDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CheckupDatabase::class.java,
                    "checkup_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}