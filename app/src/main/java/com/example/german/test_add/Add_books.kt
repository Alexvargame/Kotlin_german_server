package com.example.german.test_add

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Add_books(private val context: Context) {

    fun addbooks() {
        Log.d("TEST_DB", " Context ${context}")
        AppDatabase.resetInstance()
        context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val bookDao = db.bookDao()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_DB", "testAllWordRelatedTables() started")

            val book1 = Book(name = "Schritte plus one neu", description = "1")
            bookDao.insert(book1)
            Log.d("ADD_USER_ROLE", "Новая  role вставлена")

            val books = bookDao.getAll()
            books.forEach {
                Log.d("TEST_DB", "BOOK: ${it.name} / ${it.description}/ ${it.id}")
            }
        }
    }
}


