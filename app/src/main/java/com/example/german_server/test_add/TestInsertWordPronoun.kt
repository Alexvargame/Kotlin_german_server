package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.entities.Pronoun
import com.example.german_server.data.entities.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TestInsertPronoun(private val context: Context) {

    fun insertPronoun() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getInstance(context)
            val wordDao = db.wordDao()
            val pronounDao = db.pronounDao()
            val pronouns = listOf(
                PronounData("ich", "я", "mich", "mir", "mein", "mich", "меня", "мне", "мой", "себя"),
                PronounData("du", "ты", "dich", "dir", "dein", "dich", "тебя", "тебе", "твой", "себя"),
                PronounData("er", "он", "ihn", "ihm", "sein", "sich", "его", "ему", "его", "себя"),
                PronounData("sie", "она", "sie", "ihr", "ihr", "sich", "её", "ей", "её", "себя"),
                PronounData("es", "оно", "es", "ihm", "sein", "sich", "его", "ему", "его", "себя"),
                PronounData("wir", "мы", "uns", "uns", "unser", "uns", "нас", "нам", "наш", "себя"),
                PronounData("ihr", "вы", "euch", "euch", "euer", "euch", "вас", "вам", "ваш", "себя"),
                PronounData("sie", "они", "sie", "ihnen", "ihr", "sich", "их", "им", "их", "себя"),
                PronounData("Sie", "Вы", "Sie", "Ihnen", "Ihr", "sich", "Вас", "Вам", "Ваш", "себя"),
                )

            pronouns.forEach { data ->
                val wordId = wordDao.insert(
                    Word(
                        lectionId = 1,
                        wordTypeId = 5 // Pronoun
                    )
                )

            val pronoun = Pronoun(
                wordPtrId = wordId,
                word = data.word,
                wordTranslate = data.translate,
                akkusativ = data.akk,
                dativ = data.dat,
                prossessive = data.pos,
                reflexive = data.ref,
                akkusativTranslate = data.akkTr,
                dativTranslate = data.datTr,
                prossessiveTranslate = data.posTr,
                reflexiveTranslate = data.refTr
            )

            pronounDao.insert(pronoun)
            Log.d("PRONOUN_INSERT", "Inserted pronoun: ${data.word}")
            }

            pronounDao.getAll().forEach {
            Log.d("PRONOUN_CHECK", "Row: $it")
            }
        }
    }
}

data class PronounData(
    val word: String,
    val translate: String,
    val akk: String,
    val dat: String,
    val pos: String,
    val ref: String,
    val akkTr: String,
    val datTr: String,
    val posTr: String,
    val refTr: String
)
