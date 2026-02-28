package com.example.german_server.data.ui



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
val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Добавляем новые колонки с дефолтным значением NULL
        database.execSQL("ALTER TABLE users_baseuser ADD COLUMN serverUid TEXT")
        database.execSQL("ALTER TABLE users_baseuser ADD COLUMN loginToken TEXT")
    }
}
val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            ALTER TABLE users_baseuser 
            ADD COLUMN emailVerified INTEGER NOT NULL DEFAULT 0
            """.trimIndent()
        )
    }
}

val MIGRATION_5_6= object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            ALTER TABLE users_baseuser 
            ADD COLUMN avatar_name TEXT 
            """.trimIndent()
        )
    }
}
val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE users_baseuser ADD COLUMN active_gallery_avatar_url TEXT"
        )
        database.execSQL(
            "ALTER TABLE users_baseuser ADD COLUMN avatar_last_changed INTEGER"
        )
    }
}
val MIGRATION_7_8 = object : Migration(7, 8) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Создаём новую таблицу user_avatars
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS user_avatars (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                userId INTEGER NOT NULL,
                path TEXT NOT NULL,
                isActive INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent())
        database.execSQL("CREATE INDEX IF NOT EXISTS" +
                " index_user_avatars_userId ON user_avatars(userId)")
    }
}