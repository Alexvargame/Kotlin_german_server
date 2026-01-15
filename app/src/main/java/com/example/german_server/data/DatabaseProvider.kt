package com.example.german_server.data

import android.content.Context
import androidx.room.Room
import com.example.german_server.data.AppDatabase


object DatabaseProvider {

    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app.db"
            )
                .createFromAsset("db.sqlite3")   // ← ТУТ
                .build()

            INSTANCE = instance
            instance
        }
    }
}