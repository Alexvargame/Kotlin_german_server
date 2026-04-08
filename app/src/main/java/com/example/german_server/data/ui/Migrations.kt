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

val MIGRATION_8_9 = object : Migration(8, 9) {

    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL(
            """
            CREATE TABLE support_chat_messages (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                server_id INTEGER,
                sender_id INTEGER NOT NULL,
                receiver_id INTEGER NOT NULL,
                text TEXT NOT NULL,
                reply_to_id INTEGER,
                created_at INTEGER NOT NULL,
                is_read INTEGER NOT NULL,
                sync_status TEXT NOT NULL
            )
            """.trimIndent()
        )

        database.execSQL(
            "CREATE INDEX index_support_chat_messages_server_id ON support_chat_messages(server_id)"
        )

        database.execSQL(
            "CREATE INDEX index_support_chat_messages_created_at ON support_chat_messages(created_at)"
        )
    }
}

val MIGRATION_9_10 = object : Migration(9, 10) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Добавляем новые колонки для UID
        database.execSQL("ALTER TABLE support_chat_messages ADD COLUMN receiverUid TEXT")
        database.execSQL("ALTER TABLE support_chat_messages ADD COLUMN senderUid TEXT")
    }
}


val MIGRATION_10_11 = object : Migration(10, 11) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            ALTER TABLE users_baseuser 
            ADD COLUMN coins INTEGER DEFAULT 0
        """)

        database.execSQL("""
            ALTER TABLE users_baseuser 
            ADD COLUMN level INTEGER DEFAULT 1
        """)
        database.execSQL("""
            ALTER TABLE users_baseuser 
            ADD COLUMN lastQuestReset INTEGER NOT NULL DEFAULT 0
        """)


//        database.execSQL("""
//            UPDATE users_baseuser
//            SET level = (COALESCE(score, 0) / 100) + 1
//            WHERE level IS NULL OR level = 1
//        """)
    }
}

val MIGRATION_11_12 = object : Migration(11, 12) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // 2. Создать таблицу daily_quests с id INTEGER
        database.execSQL("""
            CREATE TABLE daily_quests (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                userId INTEGER NOT NULL,
                questTitle TEXT NOT NULL,
                conditionType TEXT NOT NULL,
                target INTEGER NOT NULL,
                progress INTEGER NOT NULL DEFAULT 0,
                isCompleted INTEGER NOT NULL DEFAULT 0,
                rewardScore INTEGER NOT NULL,
                rewardCoins INTEGER NOT NULL,
                date TEXT NOT NULL,
                FOREIGN KEY(userId) REFERENCES users_baseuser(id) ON DELETE CASCADE
            )
        """)

        // 3. Индекс
        database.execSQL("CREATE INDEX idx_daily_quests_user_date ON daily_quests(userId, date)")
    }
}
