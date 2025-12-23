package com.example.german.test_add

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.BaseUser
import com.example.german.data.entities.UserRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestDb_users(private val context: Context) {


    fun testusers() {
        Log.d("TEST_DB", " Context ${context}")
        AppDatabase.resetInstance()
        context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val BaseUserDao = db.baseUserDao()
        val userRoleDao = db.userRoleDao()
        //BaseUserDao.deleteAll()
        //userRoleDao.deleteAll()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_DB", "testAllWordRelatedTables() started")
            val user_role = UserRole(name = "Тестовая users", description = "Описание user")
            Log.d("TEST_DB", "user role сохдан с ${user_role} ")
            val userRoleId = userRoleDao.insert(user_role)
            Log.d("TEST_DB", "User role insert ${user_role} ")
            val testUser = BaseUser(
                id = 0,
                password = "testpass",
                last_login = null,
                is_superuser = false,
                registration_date = System.currentTimeMillis(),
                name = "Иван",
                surname = "Тестов",
                email = "test_${System.currentTimeMillis()}@example.com",
                username = "user_${System.currentTimeMillis()}",
                phone = null,
                last_login_date = System.currentTimeMillis(),
                is_active = true,
                is_admin = false,
                user_bot_pass = null,
                userRoleId = userRoleId,   // ← вот это ключевой параметр
                lifes = 5,
                score = 0,
                last_life_update = System.currentTimeMillis(),
                chat_id = null,
                telegram_username = null,
                user_bot_id = null
            )
            Log.d("TEST_DB", "user сохдан с ${testUser} ${testUser.userRoleId} ")
            //BaseUserDao.insert(testUser)
            try {
                BaseUserDao.insert(testUser)
                Log.d("TEST_DB", "User insert ${testUser} ")
            } catch (e: Exception) {
                Log.e("TEST_DB", "ERROR inserting user: ${e.message}")
            }


            val neeUser = BaseUser(
                id = 1,
                password = "testpass1",
                last_login = null,
                is_superuser = false,
                registration_date = System.currentTimeMillis(),
                name = "Иван1",
                surname = "Тестов1",
                email = "test_${System.currentTimeMillis()}@example.com",
                username = "user_${System.currentTimeMillis()}",
                phone = null,
                last_login_date = System.currentTimeMillis(),
                is_active = true,
                is_admin = false,
                user_bot_pass = null,
                userRoleId = userRoleId,   // ← вот это ключевой параметр
                lifes = 5,
                score = 0,
                last_life_update = System.currentTimeMillis(),
                chat_id = null,
                telegram_username = null,
                user_bot_id = null
            )
            //BaseUserDao.insert(neeUser)
            try {
                BaseUserDao.insert(neeUser)
                Log.d("TEST_DB", " New User insert ${neeUser} ")
            } catch (e: Exception) {
                Log.e("TEST_DB", "ERROR inserting user: ${e.message}")
            }
            // Чтение всех книг
            val users = BaseUserDao.getAll()
            users.forEach {
                Log.d("TEST_DB", "Book: ${it.name} / ${it.surname}")
            }

            val userToUpdate = users[0].copy(name = "Обновлённая тестовая users")
            BaseUserDao.update(userToUpdate)
            Log.d("TEST_DB", "Запись обновлена")
            // Чтение всех книг
            val updatedusers = BaseUserDao.getAll()
            updatedusers.forEach {
                Log.d("TEST_DB", "ПОсле обновления Book: ${it.name} / ${it.surname}")
                val userToDelete = updatedusers[1] // выбираем лекцию для удаления
                BaseUserDao.delete(userToDelete)       //
                Log.d("TEST_DB", "Uswer удалена")


                val finalusers = BaseUserDao.getAll()
                finalusers.forEach {
                    Log.d("TEST_DB", "Users после удаления: ${it.name} / ${it.surname}")
                }
            }
        }
    }
}


