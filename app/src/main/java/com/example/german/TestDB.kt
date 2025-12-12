package com.example.german

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.Book
import com.example.german.data.entities.Lection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestDb(private val context: Context) {

    /*fun testBookDao() {
        val db = AppDatabase.getInstance(context) // используем метод получения базы
        val bookDao = db.bookDao()
        CoroutineScope(Dispatchers.IO).launch {
            // Вставка тестовой книги
            val testBook = Book(name = "Тестовая книга", description = "Описание книги")
            bookDao.insert(testBook)

            val newBook = Book(name = "Новая тестовая книга", description = "Описание новой книги")
            bookDao.insert(newBook)
            Log.d("TEST_DB", "Новая тестовая запись вставлена")


            // Чтение всех книг
            val books = bookDao.getAll()
            books.forEach {
                Log.d("TEST_DB", "Book: ${it.name} / ${it.description}")
            }

            val bookToUpdate = books[0].copy(name = "Обновлённая тестовая книга")
            bookDao.update(bookToUpdate)
            Log.d("TEST_DB", "Запись обновлена")
            // Чтение всех книг
            val updatedbooks = bookDao.getAll()
            updatedbooks.forEach {
                Log.d("TEST_DB", "ПОсле обновления Book: ${it.name} / ${it.description}")
            val bookToDelete = updatedbooks[1] // выбираем книгу для удаления
            bookDao.delete(bookToDelete)       // метод delete должен быть в BookDao
            Log.d("TEST_DB", "Книга удалена")


            val finalBooks = bookDao.getAll()
            finalBooks.forEach {
                Log.d("TEST_DB", "Book после удаления: ${it.name} / ${it.description}")
            }
        }
        }
    }*/
    fun testLectionDao() {
        Log.d("TEST_DB", " Context ${context}")
        AppDatabase.resetInstance()
        context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val lectionDao = db.lectionDao()
        val bookDao = db.bookDao()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_DB", "testAllWordRelatedTables() started")
            val book = Book(name = "Тестовая книга", description = "Описание книги")
            val bookId = bookDao.insert(book)
            // Вставка тестовой книги
            val testLection = Lection(name = "Тестовая лекция", description = "Описание лекции",
                bookId = bookId )
            lectionDao.insert(testLection)

            val newLection = Lection(name = "Новая тестовая лекция", description = "Описание новой лекции",
                bookId = bookId )
            lectionDao.insert(newLection)
            Log.d("TEST_DB", "Новая тестовая лекциячя вставлена")


            // Чтение всех книг
            val lections = lectionDao.getAll()
            lections.forEach {
                Log.d("TEST_DB", "Book: ${it.name} / ${it.description}")
            }

            val lectionToUpdate = lections[0].copy(name = "Обновлённая тестовая лекция")
            lectionDao.update(lectionToUpdate)
            Log.d("TEST_DB", "Запись обновлена")
            // Чтение всех книг
            val updatedlections = lectionDao.getAll()
            updatedlections.forEach {
                Log.d("TEST_DB", "ПОсле обновления Book: ${it.name} / ${it.description}")
                val lectionToDelete = updatedlections[1] // выбираем лекцию для удаления
                lectionDao.delete(lectionToDelete)       //
                Log.d("TEST_DB", "Лекция удалена")


                val finalLections = lectionDao.getAll()
                finalLections.forEach {
                    Log.d("TEST_DB", "Lection после удаления: ${it.name} / ${it.description}")
                }
            }
        }
    }
}


