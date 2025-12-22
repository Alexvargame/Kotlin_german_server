package com.example.german

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.UserRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestD_books(private val context: Context) {


    fun testbooks() {
        Log.d("TEST_DB_books", " Context ${context}")
        AppDatabase.resetInstance()
        context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val userRoleDao = db.userRoleDao()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_DB", "testAllWordRelatedTables() started")

            // Вставка тестовой книги
            val testUserRole = UserRole(name = "Тестовая book", description = "Описание book")
            userRoleDao.insert(testUserRole)

            val newUserRole = UserRole(name = "Новая тестовая book", description = "Описание новой book")
            userRoleDao.insert(newUserRole)
            Log.d("TEST_DB", "Новая тестовая book ставлена")


            // Чтение всех книг
            val userRoles = userRoleDao.getAll()
            userRoles.forEach {
                Log.d("TEST_DB", "Book: ${it.name} / ${it.description}")
            }

            val userRoleToUpdate = userRoles[0].copy(name = "Обновлённая тестовая book")
            userRoleDao.update(userRoleToUpdate)
            Log.d("TEST_DB", "Запись обновлена")

            val updateduserRole = userRoleDao.getAll()
            updateduserRole.forEach {
                Log.d("TEST_DB", "ПОсле обновления Book: ${it.name} / ${it.description}")
                val userRoleToDelete = updateduserRole[1] // выбираем лекцию для удаления
                userRoleDao.delete(userRoleToDelete)       //
                Log.d("TEST_DB", "Лекция удалена")


                val finaluserRoles = userRoleDao.getAll()
                finaluserRoles.forEach {
                    Log.d("TEST_DB", "Lection после удаления: ${it.name} / ${it.description}")
                }
            }
        }
    }
}


