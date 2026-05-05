package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.InputStreamReader

import com.example.german_server.data.entities.Noun
import com.example.german_server.data.entities.Word
import java.io.BufferedReader
import java.nio.charset.Charset


//class CsvNounImporter(private val context: Context) {
//
//    fun importFromAssets() {
//        CoroutineScope(Dispatchers.IO).launch {
//            importInternal()
//        }
//    }
//
//    private suspend fun importInternal() {
//
//        val db = AppDatabase.getInstance(context)
//        val wordDao = db.wordDao()
//        val nounDao = db.nounDao()
//
//        val inputStream = context.assets.open("nouns.csv")
//
//        BufferedReader(InputStreamReader(inputStream)).use { reader ->
//
//            reader.readLine() // header
//
//            // 💥 ВАЖНО: один Word на весь импорт (как у тебя в тесте)
//            val wordId = wordDao.insert(
//                Word(
//                    lectionId = 45,
//                    wordTypeId = 1
//                )
//            )
//
//            var line: String?
//
//            while (reader.readLine().also { line = it } != null) {
//
//                val parts = line!!.split(";")
//                if (parts.size < 6) continue
//
//                val articleId = parts[2].toLongOrNull() ?: continue
//
//                nounDao.insert(
//                    Noun(
//                        wordPtrId = wordId,
//                        word = parts[0],
//                        wordTranslate = parts[1],
//                        articleId = articleId,
//                        wordPlural = parts[3].takeIf { it != "null" },
//                        pluralSign = parts[4].takeIf { it != "null" },
//                        wordTranslatePlural = parts[5].takeIf { it != "null" }
//                    )
//                )
//            }
//        }
//    }
//}
class TestCsvImport(private val context: Context) {

    fun runTestCsvImport() {
        CoroutineScope(Dispatchers.IO).launch {

            val db = AppDatabase.getInstance(context)
            val wordDao = db.wordDao()
            val nounDao = db.nounDao()

            val inputStream = context.assets.open("beruf_nouns_7.csv")

            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                Log.d("CSV_TEST", "Begin: ")
//                val reader = BufferedReader(
//                    InputStreamReader(inputStream, Charset.forName("windows-1251"))
//                )
                reader.readLine() // пропускаем header

                var line: String?

                while (true) {
                    line = reader.readLine() ?: break

                    val parts = line.replace("\"", "").split(";")

                    Log.d("CSV_TEST", "Parts_line: ${parts}, ${parts.size}")
                    if (parts.size < 6) continue

                    val wordId = wordDao.insert(
                        Word(
                            lectionId = 45,
                            wordTypeId = 1
                        )
                    )
                    Log.d("CSV_TEST", "WordID: ${wordId}")

                    val noun = Noun(
                        wordPtrId = wordId,
                        word = parts[0],
                        wordTranslate = parts[1],
                        articleId = parts[2].toLong(),
                        wordPlural = parts[3],
                        pluralSign = parts[4],
                        wordTranslatePlural = parts[5]
                    )

//                    nounDao.insert(noun)

                    Log.d("CSV_TEST", "Inserted: ${noun.word}")
                }
            }
            val lastTwo = nounDao.getLastTwo()

            lastTwo.forEach {
                Log.d("CSV_TEST", "CHECK: $it")
            }
            // 💥 проверка
//            nounDao.getAll().forEach {
//                Log.d("CSV_RESULT", it.toString())
//            }
        }
    }
}