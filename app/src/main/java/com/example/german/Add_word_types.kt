package com.example.german

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.WordType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Add_word_types(private val context: Context) {

    fun addwordtypes() {
        Log.d("TEST_DB", " Context ${context}")
        //AppDatabase.resetInstance()
        //context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val wordTypeDao = db.wordTypeDao()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_DB", "testAllWordRelatedTables() started")


            val word_type1 = WordType(
                name = "Noun",
                description = "Существительное",
            )
            wordTypeDao.insert(word_type1)
            Log.d("WordTp1", "Новая  Art1 вставлен")
            val word_type2 = WordType(
                name = "Verb",
                description = "Глаголы",
            )
            wordTypeDao.insert(word_type2)
            Log.d("WordTp2", "Новая  Art2 вставлен")
            val word_type3= WordType(
                name = "Adjective",
                description = "Прилагательные",
            )
            wordTypeDao.insert(word_type3)
            Log.d("WordTp3", "Новая  Art3 вставлен")
            val word_type4= WordType(
                name = "Pronoun",
                description = "Местоимения",
            )
            wordTypeDao.insert(word_type4)
            Log.d("WordTp4", "Новая  Art4 вставлен")
            val word_type5 = WordType(
                name = "Numeral",
                description = "Числительные",
            )
            wordTypeDao.insert(word_type5)
            Log.d("WordTp5", "Новая  Art5вставлен")
            val word_type6 = WordType(
                name = "OtherWord",
                description = "Остальные слова",
            )
            wordTypeDao.insert(word_type6)
            Log.d("WordTp6", "Новая  Art6 вставлен")

            val wordtypes = wordTypeDao.getAll()
            wordtypes.forEach {
                Log.d("TEST_DB", "USER_ROLE: ${it.name} / ${it.description}/ ${it.id}")
            }
        }
    }
}


