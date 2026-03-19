package com.example.german_server.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.german_server.data.dao.*
import com.example.german_server.data.entities.*
import com.example.german_server.data.ui.MIGRATION_1_2
import com.example.german_server.data.ui.MIGRATION_2_3
import com.example.german_server.data.ui.MIGRATION_3_4
import com.example.german_server.data.ui.MIGRATION_4_5
import com.example.german_server.data.ui.MIGRATION_5_6
import com.example.german_server.data.ui.MIGRATION_6_7
import com.example.german_server.data.ui.MIGRATION_7_8
import com.example.german_server.data.ui.MIGRATION_8_9
import com.example.german_server.data.ui.MIGRATION_9_10
//import android.util.Log


@Database(
    entities = [
        Book::class,
        Lection::class,
        Word::class,
        WordType::class,
        Adjective::class,
        Noun::class,
        Verb::class,
        BaseUser::class,
        UserRole::class,
        CallbackSiteMessage::class,
        Article::class,
        Pronoun::class,
        OtherWord::class,
        Numeral::class,
        NounDeclensionsForm::class,
        UserAvatar::class,
        SupportChatMessage::class,

    ],
    version = 10
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun lectionDao(): LectionDao
    abstract fun wordDao(): WordDao
    abstract fun wordTypeDao(): WordTypeDao
    abstract fun adjectiveDao(): AdjectiveDao
    abstract fun nounDao(): NounDao
    abstract fun verbDao(): VerbDao
    abstract fun baseUserDao(): BaseUserDao
    abstract fun userRoleDao(): UserRoleDao
    abstract fun callbackSiteMessageDao(): CallbackSiteMessageDao
    abstract fun articleDao(): ArticleDao
    abstract fun pronounDao(): PronounDao
    abstract fun numeralDao(): NumeralDao
    abstract fun otherWordDao(): OtherWordDao
    abstract fun nounDeclensionsFormDao(): NounDeclensionsFormDao

    abstract fun registrationDao(): UserRegistrationDao

    abstract fun userAvatarDao(): UserAvatarDao

    abstract fun supportChatMessageDao(): SupportChatMessageDao



    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app.db"
                )

                    .createFromAsset("databases/app.db")
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .addMigrations(MIGRATION_3_4)
                    .addMigrations(MIGRATION_4_5)
                    .addMigrations(MIGRATION_5_6)
                    .addMigrations(MIGRATION_6_7)
                    .addMigrations(MIGRATION_7_8)
                    .addMigrations(MIGRATION_8_9)
                    .addMigrations(MIGRATION_9_10)
                    .build()
                INSTANCE = instance
                instance
            }
        }
        fun resetInstance() {
            INSTANCE = null
        }
    }
}
