package com.example.german

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.Noun
import com.example.german.data.entities.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

class TestInsertWord(private val context: Context) {

    fun testInsertWord() {
        CoroutineScope(Dispatchers.IO).launch {

            val db = AppDatabase.Companion.getInstance(context)
            val wordDao = db.wordDao()

           /*val wordId = wordDao.insert(
                Word(
                    lectionId = 1,//lectionId,      // ← СУЩЕСТВУЮЩИЙ
                    wordTypeId = 1//nounTypeId     // ← WordType "Noun"
                )
            )*/
            val nounDao = db.nounDao()
            //Log.d("STEP1", "Inserted Word id = $wordId")

            nounDao.deleteAll()

            nounDao.getAll().forEach {
                Log.d("NOUNS", "Noun row: $it")
            }
            val nounsToInsert = listOf(
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Mann",
                    wordTranslate = "мужчина",
                    articleId = 1,
                    wordPlural = "Männer",
                    wordTranslatePlural = "мужчины",
                    pluralSign = "er"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Frau",
                    wordTranslate = "женщина",
                    articleId = 3,
                    wordPlural = "Frauen",
                    wordTranslatePlural = "женщины",
                    pluralSign = "en"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Kind",
                    wordTranslate = "ребёнок",
                    articleId = 2,
                    wordPlural = "Kinder",
                    wordTranslatePlural = "дети",
                    pluralSign = "er"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Auto",
                    wordTranslate = "машина",
                    articleId = 2,
                    wordPlural = "Autos",
                    wordTranslatePlural = "машины",
                    pluralSign = "s"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Baum",
                    wordTranslate = "дерево",
                    articleId = 1,
                    wordPlural = "Bäume",
                    wordTranslatePlural = "деревья",
                    pluralSign = "e"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Blume",
                    wordTranslate = "цветок",
                    articleId = 3,
                    wordPlural = "Blumen",
                    wordTranslatePlural = "цветы",
                    pluralSign = "n"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Tisch",
                    wordTranslate = "стол",
                    articleId = 1,
                    wordPlural = "Tische",
                    wordTranslatePlural = "столы",
                    pluralSign = "e"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Stuhl",
                    wordTranslate = "стул",
                    articleId = 1,
                    wordPlural = "Stühle",
                    wordTranslatePlural = "стулья",
                    pluralSign = "e"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Wasser",
                    wordTranslate = "вода",
                    articleId = 2,
                    wordPlural = null,
                    wordTranslatePlural = null,
                    pluralSign = null
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Brot",
                    wordTranslate = "хлеб",
                    articleId = 2,
                    wordPlural = "Brote",
                    wordTranslatePlural = "хлеба",
                    pluralSign = "e"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Apfel",
                    wordTranslate = "яблоко",
                    articleId = 1,
                    wordPlural = "Äpfel",
                    wordTranslatePlural = "яблоки",
                    pluralSign = "e"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Milch",
                    wordTranslate = "молоко",
                    articleId = 3,
                    wordPlural = null,
                    wordTranslatePlural = null,
                    pluralSign = null
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Zeit",
                    wordTranslate = "время",
                    articleId = 3,
                    wordPlural = "Zeiten",
                    wordTranslatePlural = "времена",
                    pluralSign = "en"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Tag",
                    wordTranslate = "день",
                    articleId = 1,
                    wordPlural = "Tage",
                    wordTranslatePlural = "дни",
                    pluralSign = "e"
                ),
                Noun(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 1)),
                    word = "Leute",
                    wordTranslate = "люди",
                    articleId = 4,
                    wordPlural = null,
                    wordTranslatePlural = null,
                    pluralSign = null
                )
            )
            nounsToInsert.forEach { noun ->
                nounDao.insert(noun)
                Log.d("STEP_NOUN", "Inserted Noun: ${noun.word}")
            }


            Log.d("STEP2", "Inserted Noun id = 1")
            nounDao.getAll().forEach {
                Log.d("NOUNS", "Noun row: $it")
            }

        }
    }
}