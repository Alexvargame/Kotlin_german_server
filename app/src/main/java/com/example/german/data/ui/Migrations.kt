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

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL("""
            ALTER TABLE users_baseuser 
            ADD COLUMN shockmod_begin INTEGER 
        """.trimIndent())

        database.execSQL("""
            ALTER TABLE users_baseuser 
            ADD COLUMN shockmod_now INTEGER
        """.trimIndent())

        database.execSQL("""
            ALTER TABLE users_baseuser 
            ADD COLUMN shockmod_long INTEGER NOT NULL DEFAULT 0
        """.trimIndent())
    }
}
