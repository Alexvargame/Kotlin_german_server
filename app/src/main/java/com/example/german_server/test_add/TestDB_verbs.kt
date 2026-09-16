package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.entities.Verb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
class TestDb_verbs(private val context: Context) {


    fun test_verbs() {
        Log.d("TEST_DB", " Context ${context}")
      //  AppDatabase.resetInstance()
      //  context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
      //  Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val verbDao = db.verbDao()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_VERBS", "testAllWordRelatedTables() started")


            // Чтение всех книг
            val verbs = verbDao.getAll()
            verbs.forEach {
                Log.d("TEST_verb", "Book: ${it.word} / ${it.preposition} / ${it.prepositionCase}")
            }
            val text = verbs.joinToString("\n") { it.word }

            val file = File(context.getExternalFilesDir(null), "verbs.txt")
            file.writeText(text)
            println("Файл: ${file.absolutePath}")
            Log.d("TEST_verb", "File exists: ${file.exists()}")
            Log.d("TEST_verb", "File path: ${file.absolutePath}")
            Log.d("TEST_verb", "File size: ${file.length()}")


        }
    }
}


