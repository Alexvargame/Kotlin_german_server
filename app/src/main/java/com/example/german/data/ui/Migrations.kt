package com.example.german.data.ui



import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Миграция с версии 1 на 2 (подставь свои версии)
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Добавляем новую колонку avatar_path
        database.execSQL("""
            ALTER TABLE users_baseuser
            ADD COLUMN avatar_path TEXT
        """.trimIndent())
    }
}
