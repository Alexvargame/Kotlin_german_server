package com.example.german.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.german.data.dao.*
import com.example.german.data.entities.*

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
    ],
    version = 1
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
                    // Если используешь готовую базу из assets
                    //.createFromAsset("old_database.db")
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
