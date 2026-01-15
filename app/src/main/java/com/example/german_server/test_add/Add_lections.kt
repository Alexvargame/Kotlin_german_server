package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.entities.Lection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Add_lections(private val context: Context) {

    fun addlections() {
        Log.d("TEST_DB", " Context ${context}")
        //AppDatabase.resetInstance()
        //context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_APP_DB", "DB path: ${context.getDatabasePath("app.db")}")
        val lectionDao = db.lectionDao()
        //val bookDao = db.bookDao()
        CoroutineScope(Dispatchers.IO).launch {
            //val book = Book(name = "Schritte plus one neu", description = "1")
            Log.d("TEST_DB", "testAllWordRelatedTables() started")

            val Lection1 = Lection(name = "Familie", description = "Book_1", bookId = 1)
            lectionDao.insert(Lection1)
            Log.d("Lection", "Новая  lection вставлена")

            val lections = lectionDao.getAll()
            lections.forEach {
                Log.d("TEST_DB", "LEction: ${it.name} / ${it.description}/ ${it.id}")
            }
        }
    }
}


