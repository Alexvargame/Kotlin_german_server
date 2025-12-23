package com.example.german.test_add

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.Adjective
import com.example.german.data.entities.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson



class TestInsertWordBAdjective(private val context: Context) {

    fun testInsertWord() {
        CoroutineScope(Dispatchers.IO).launch {

            val db = AppDatabase.Companion.getInstance(context)
            val wordDao = db.wordDao()

           val wordId = wordDao.insert(
                Word(
                    lectionId = 1,//lectionId,      // ← СУЩЕСТВУЮЩИЙ
                    wordTypeId = 3//nounTypeId     // ← WordType "Noun"
                )
            )
            val adjectiveDao = db.adjectiveDao()
            Log.d("STEP1", "Inserted Word id = $wordId")

            val gson = Gson()

            val adjectives = listOf(
                Adjective(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 3)),
                    word = "schnell",
                    wordTranslate = "быстрый",
                    komparativ = "schneller",
                    superlativ = "am schnellsten",
                    declensions = gson.toJson(
                        mapOf("nom" to "schnell", "acc" to "schnellen", "dat" to "schnellem")
                    )
                ),
                Adjective(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 3)),
                    word = "groß",
                    wordTranslate = "большой",
                    komparativ = "größer",
                    superlativ = "am größten",
                    declensions = gson.toJson(
                        mapOf("nom" to "groß", "acc" to "großen", "dat" to "großem")
                    )
                ),
                Adjective(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 3)),
                    word = "klein",
                    wordTranslate = "маленький",
                    komparativ = "kleiner",
                    superlativ = "am kleinsten",
                    declensions = gson.toJson(
                        mapOf("nom" to "klein", "acc" to "kleinen", "dat" to "kleinem")
                    )
                )
            )

            adjectives.forEach { verb ->
                adjectiveDao.insert(verb)
                Log.d("STEP2", "Inserted Verb: ${verb.word}")
            }


            Log.d("STEP2", "Inserted Adj id = ")
            adjectiveDao.getAll().forEach {
                Log.d("STEP2", "Adj row: $it")
            }

        }
    }
}