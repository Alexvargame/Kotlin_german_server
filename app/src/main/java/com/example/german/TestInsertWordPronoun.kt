package com.example.german

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.Pronoun
import com.example.german.data.entities.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson



class TestInsertPronoun(private val context: Context) {

    fun insertPronoun() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getInstance(context)
            val wordDao = db.wordDao()
            val pronounDao = db.pronounDao()

            // 1️⃣ Создаем Word для Numeral
            val wordId = wordDao.insert(
                Word(
                    lectionId = 1,     // существующий Lection.id
                    wordTypeId =5     // WordType.id для Numeral
                )
            )
            Log.d("PRONUOL_TEST", "Inserted Word for pronoun id = $wordId")

            // 2️⃣ Создаем Numeral
            val pronoun = Pronoun(
                wordPtrId = wordId,        // ссылка на Word
                word = "ich",              // немецкое местоимение
                wordTranslate = "я",       // перевод
                akkusativ = "mich",
                dativ = "mir",
                prossessive = "mein",
                reflexive = "mich",
                akkusativTranslate = "меня",
                dativTranslate = "мне",
                prossessiveTranslate = "мой",
                reflexiveTranslate = "себя"
            )
            pronounDao.insert(pronoun)

            pronounDao.insert(pronoun)
            Log.d("NUMERAL_TEST", "Inserted pronuonl: ${pronoun.word}")

            // 3️⃣ Проверка
            pronounDao.getAll().forEach {
                Log.d("NUMERAL_TEST", "Pronounl row: $it")
            }
        }
    }
}