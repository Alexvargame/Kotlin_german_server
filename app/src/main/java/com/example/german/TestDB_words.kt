package com.example.german

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.Pronoun
import com.example.german.data.entities.OtherWord
import com.example.german.data.entities.Numeral
import com.example.german.data.entities.NounDeclensionsForm
import com.example.german.data.entities.Word
import com.example.german.data.entities.Noun
import com.example.german.data.entities.Verb
import com.example.german.data.entities.WordType
import com.example.german.data.entities.Article
import com.example.german.data.entities.Adjective
import com.example.german.data.entities.Book
import com.example.german.data.entities.Lection

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestDb_words(private val context: Context) {

    fun testAllWordRelatedTables() {
        Log.d("TEST_DB", "${context}")
        val db = AppDatabase.getInstance(context)
        Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val wordDao = db.wordDao()
        val articleDao = db.articleDao()
        val verbDao = db.verbDao()
        val nounDao = db.nounDao()
        val pronounDao = db.pronounDao()
        val otherWordDao = db.otherWordDao()
        val numeralDao = db.numeralDao()
        val nounDeclDao = db.nounDeclensionsFormDao()
        val adjectiveDao = db.adjectiveDao()
        val wordTypeDao = db.wordTypeDao()
        val lectionDao = db.lectionDao()
        val bookDao = db.bookDao()

        CoroutineScope(Dispatchers.IO).launch {


            val book = Book(name = "Тестовая книга", description = "Описание книги")
            Log.d("TEST_DB", "Word сохдан с ${book} ")
            val bookId = bookDao.insert(book)
            Log.d("TEST_DB", "Книга вставлена с ${book} ")
            val testLection = Lection(
                name = "Тестовая лекция", description = "Описание лекции",
                bookId = bookId
            )
            Log.d("TEST_DB", "Лекйияч сохдан с ${testLection} ")
            val lectionId = lectionDao.insert(testLection)
            Log.d("TEST_DB", "Лекйич вставлнеа ")
            val wordType = WordType(name = "существительное")
            Log.d("TEST_DB", "тИП сохдан с ${wordType} ")
            val wordTypeId = wordTypeDao.insert(wordType)
            Log.d("TEST_DB", "ЛеТИП ВТСАЛВЕНс ${wordType} ")
            // 1️⃣ Вставляем Word
            val word = Word(
                lectionId = lectionId,      // например, ID существующей лекции
                wordTypeId = wordTypeId      // например, тип слова
            )
            Log.d("TEST_DB", "Word сохдан с id ")
            val wordId = wordDao.insert(word)
            Log.d("TEST_DB", "Word вставлен с id = $wordId")

            // 2️⃣ Вставляем Article для Noun
            val article = Article(name = "der", description = "мужской")
            val articleId = articleDao.insert(article)
            Log.d("TEST_DB", "Article вставлен: ${article.name}")

            // 3️⃣ Вставка Verb
            val verb = Verb(
                wordPtrId = wordId,
                word = "laufen",
                wordTranslate = "бежать",
                ichForm = "laufe",
                duForm = "läufst",
                erSieEsForm = "läuft",
                wirForm = "laufen",
                ihrForm = "lauft",
                sieSieForm = "laufen",
                regal = false
            )
            verbDao.insert(verb)
            Log.d("TEST_DB", "Verb вставлен: ${verb.word}")

            val verbs = verbDao.getAll()
            val verbToUpdate = verbs[0].copy(word = "laufen_")
            verbDao.update(verbToUpdate)
            Log.d("TEST_DB", "Запись обновлена")
            val updatedverb = verbDao.getAll()
            updatedverb.forEach {
                Log.d("TEST_DB", "ПОсле обновления verb: ${it.word} / ${it.wordTranslate}")
                val verbToDelete = updatedverb[1] // выбираем лекцию для удаления
                verbDao.delete(verbToDelete)       //
                Log.d("TEST_DB", "verb удалена")
            }

            // 4️⃣ Вставка Noun
            val noun = Noun(
                wordPtrId = wordId,
                articleId = articleId,
                word = "Lauf",
                wordTranslate = "бег"
            )
            nounDao.insert(noun)
            Log.d("TEST_DB", "Noun вставлен: ${noun.word}")

            val nouns = nounDao.getAll()
            val nounToUpdate = nouns[0].copy(word = "lauf_")
            nounDao.update(nounToUpdate)
            Log.d("TEST_DB", "Запись обновлена")
            val updatednoun = nounDao.getAll()
            updatednoun.forEach {
                Log.d("TEST_DB", "ПОсле обновления noun : ${it.word} / ${it.wordTranslate}")
                val nounToDelete = updatednoun[1] // выбираем лекцию для удаления
                nounDao.delete(nounToDelete)       //
                Log.d("TEST_DB", "noun удалена")
            }

            // 5️⃣ Вставка Pronoun
            val pronoun = Pronoun(
                wordPtrId = wordId,
                word = "ich",
                wordTranslate = "я"
            )
            pronounDao.insert(pronoun)
            Log.d("TEST_DB", "Pronoun вставлен: ${pronoun.word}")

            val pronouns = pronounDao.getAll()
            val pronounToUpdate = pronouns[0].copy(word = "ich_")
            pronounDao.update(pronounToUpdate)
            Log.d("TEST_DB", "Запись обновлена")
            val updatedpronoun = pronounDao.getAll()
            updatedpronoun.forEach {
                Log.d("TEST_DB", "ПОсле обновления PRO: ${it.word} / ${it.wordTranslate}")
                val pronounToDelete = updatedpronoun[1] // выбираем лекцию для удаления
                pronounDao.delete(pronounToDelete)       //
                Log.d("TEST_DB", "pronoun удалена")
            }

            // 6️⃣ Вставка OtherWord
            val otherWord = OtherWord(
                wordPtrId = wordId,
                word = "und",
                wordTranslate = "и"
            )
            otherWordDao.insert(otherWord)
            Log.d("TEST_DB", "OtherWord вставлен: ${otherWord.word}")

            val otherWords = otherWordDao.getAll()
            val otherWordToUpdate = otherWords[0].copy(word = "eins_")
            otherWordDao.update(otherWordToUpdate)
            Log.d("TEST_DB", "Запись обновлена")
            val updatedotherWord = otherWordDao.getAll()
            updatedotherWord.forEach {
                Log.d("TEST_DB", "ПОсле обновления Book: ${it.word} / ${it.wordTranslate}")
                val otherWordToDelete = updatedotherWord[1] // выбираем лекцию для удаления
                otherWordDao.delete(otherWordToDelete)       //
                Log.d("TEST_DB", "otherWordудалена")
            }

            // 7️⃣ Вставка Numeral
            val numeral = Numeral(
                wordPtrId = wordId,
                word = "eins",
                wordTranslate = "один"
            )


            numeralDao.insert(numeral)
            Log.d("TEST_DB", "Numeral вставлен: ${numeral.word}")
            val numerals = numeralDao.getAll()
            val numeralToUpdate = numerals[0].copy(word = "eins_")
            numeralDao.update(numeralToUpdate)
            Log.d("TEST_DB", "Запись обновлена")
            val updatednumeral = numeralDao.getAll()
            updatednumeral.forEach {
                Log.d("TEST_DB", "ПОсле обновления Book: ${it.word} / ${it.wordTranslate}")
                val numeralToDelete = updatednumeral[1] // выбираем лекцию для удаления
                numeralDao.delete(numeralToDelete)       //
                Log.d("TEST_DB", "numeral удалена")
            }

            // 8️⃣ Вставка NounDeclensionsForm
            val nounDecl = NounDeclensionsForm(
                nounId = noun.wordPtrId
            )
            nounDeclDao.insert(nounDecl)
            Log.d("TEST_DB", "NounDeclensionsForm вставлен для nounId: ${nounDecl.nounId}")

            // 9️⃣ Вставка Adjective
            val adjective = Adjective(
                wordPtrId = wordId,
                word = "schnell",
                wordTranslate = "быстрый"
            )
            adjectiveDao.insert(adjective)
            Log.d("TEST_DB", "Adjective вставлен: ${adjective.word}")
            val adjectives = adjectiveDao.getAll()
            val adjectiveToUpdate = adjectives[0].copy(word = "schnell_")
            adjectiveDao.update(adjectiveToUpdate)
            Log.d("TEST_DB", "Запись обновлена")
            val updatedadjective = adjectiveDao.getAll()
            updatedadjective.forEach {
                Log.d("TEST_DB", "ПОсле обновления Book: ${it.word} / ${it.wordTranslate}")
                val adjectiveToDelete = updatedadjective[1] // выбираем лекцию для удаления
                adjectiveDao.delete(adjectiveToDelete)       //
                Log.d("TEST_DB", "Adjective удалена")

        }

            // 🔟 Чтение всех данных для проверки
            verbDao.getAll().forEach { Log.d("TEST_DB", "Verb: ${it.word} / ${it.wordTranslate}") }
            nounDao.getAll().forEach { Log.d("TEST_DB", "Noun: ${it.word} / ${it.wordTranslate}") }
            pronounDao.getAll().forEach { Log.d("TEST_DB", "Pronoun: ${it.word} / ${it.wordTranslate}") }
            otherWordDao.getAll().forEach { Log.d("TEST_DB", "OtherWord: ${it.word} / ${it.wordTranslate}") }
            numeralDao.getAll().forEach { Log.d("TEST_DB", "Numeral: ${it.word} / ${it.wordTranslate}") }
            nounDeclDao.getAll().forEach { Log.d("TEST_DB", "NounDeclensionsForm id: ${it.id} / nounId: ${it.nounId}") }
            adjectiveDao.getAll().forEach { Log.d("TEST_DB", "Adjective: ${it.word} / ${it.wordTranslate}") }
        }
    }

}


