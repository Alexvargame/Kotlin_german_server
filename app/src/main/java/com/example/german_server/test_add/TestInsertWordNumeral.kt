package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.entities.Numeral
import com.example.german_server.data.entities.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TestInsertNumeral(private val context: Context) {

    fun insertOneNumeral() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getInstance(context)
            val wordDao = db.wordDao()
            val numeralDao = db.numeralDao()

            // 1️⃣ Создаем Word для Numeral
            val wordId = wordDao.insert(
                Word(
                    lectionId = 1,     // существующий Lection.id
                    wordTypeId =4     // WordType.id для Numeral
                )
            )
            Log.d("NUMERAL_TEST", "Inserted Word for Numeral id = $wordId")

            // 2️⃣ Создаем Numeral
            val numeral = Numeral(
                wordPtrId = wordId,
                word = "eins",
                wordTranslate = "один",
                ordinal = "erste",
                dateNumeral = "erster"
            )

            numeralDao.insert(numeral)
            Log.d("NUMERAL_TEST", "Inserted Numeral: ${numeral.word}")

            // 3️⃣ Проверка
            numeralDao.getAll().forEach {
                Log.d("NUMERAL_TEST", "Numeral row: $it")
            }
        }
    }
}