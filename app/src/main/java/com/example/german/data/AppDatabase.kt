package com.example.german.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WordEntity::class],  // ← имя твоей таблицы
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao   // ← твой DAO
}