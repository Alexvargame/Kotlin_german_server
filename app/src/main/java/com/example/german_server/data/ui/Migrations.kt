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
val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Если нужно — можно просто ALTER TABLE
        // В этом случае поле есть, меняется только var/val — Room иногда не требует SQL, но лучше явно указать
        database.execSQL("ALTER TABLE users_baseuser RENAME TO tmp_users_baseuser")
        database.execSQL("""
            CREATE TABLE users_baseuser (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                email TEXT,
                emailVerified INTEGER NOT NULL DEFAULT 0,
                ... // остальные поля
            )
        """)
        database.execSQL("""
            INSERT INTO users_baseuser (id, email, emailVerified, ...) 
            SELECT id, email, emailVerified, ... FROM tmp_users_baseuser
        """)
        database.execSQL("DROP TABLE tmp_users_baseuser")
    }
}
