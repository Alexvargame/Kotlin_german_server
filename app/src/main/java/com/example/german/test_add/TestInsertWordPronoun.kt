package com.example.german.test_add

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.Pronoun
import com.example.german.data.entities.Word
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
                //PronounData("ich", "я", "mich", "mir", "mein", "mich", "меня", "мне", "мой", "себя"),
                PronounData("du", "ты", "dich", "dir", "dein", "dich", "тебя", "тебе", "твой", "себя"),
                PronounData("er", "он", "ihn", "ihm", "sein", "sich", "его", "ему", "его", "себя"),
                PronounData("sie", "она", "sie", "ihr", "ihr", "sich", "её", "ей", "её", "себя"),
                PronounData("es", "оно", "es", "ihm", "sein", "sich", "его", "ему", "его", "себя"),
                PronounData("wir", "мы", "uns", "uns", "unser", "uns", "нас", "нам", "наш", "себя"),
                PronounData("ihr", "вы", "euch", "euch", "euer", "euch", "вас", "вам", "ваш", "себя"),
                PronounData("sie", "они", "sie", "ihnen", "ihr", "sich", "их", "им", "их", "себя"),
                PronounData("Sie", "Вы", "Sie", "Ihnen", "Ihr", "sich", "Вас", "Вам", "Ваш", "себя"),
                )
            // 1️⃣ Создаем Word для Numeral
            /*val wordId = wordDao.insert(
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
            }*/
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

            //pronounDao.insert(pronoun)
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
