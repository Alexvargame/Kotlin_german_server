package com.example.german

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.OtherWord
import com.example.german.data.entities.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson



class TestInsertOtherWordl(private val context: Context) {

    fun insertOtherWord() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getInstance(context)
            val wordDao = db.wordDao()
            val otherWordDao = db.otherWordDao()

            // 1️⃣ Создаем Word для Numeral
            val wordId = wordDao.insert(
                Word(
                    lectionId = 1,     // существующий Lection.id
                    wordTypeId =6     // WordType.id для Numeral
                )
            )
            Log.d("NUMERAL_TEST", "Inserted Word for Numeral id = $wordId")

            // 2️⃣ Создаем Numeral
            val otherWord = OtherWord(
                wordPtrId = wordId,
                word = "schnell",
                wordTranslate = "быстро"
            )
            otherWordDao.insert(otherWord)


            otherWordDao.insert(otherWord)
            Log.d("NUMERAL_TEST", "Inserted Numeral: ${otherWord.word}")

            // 3️⃣ Проверка
            otherWordDao.getAll().forEach {
                Log.d("NUMERAL_TEST", "Numeral row: $it")
            }
        }
    }
}