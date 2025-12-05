package com.example.german.data

import android.content.Context
import androidx.room.Room
import com.example.german.data.AppDatabase


object DatabaseProvider {

    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app.db"
            )
                .createFromAsset("old_database.db")   // ← ТУТ
                .build()

            INSTANCE = instance
            instance
        }
    }
}