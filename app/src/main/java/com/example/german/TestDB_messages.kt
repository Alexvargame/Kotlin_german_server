package com.example.german

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.BaseUser
import com.example.german.data.entities.UserRole
import com.example.german.data.entities.CallbackSiteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestDb_messages(private val context: Context) {


    fun testmessages() {
        Log.d("TEST_DB", " Context ${context}")
        AppDatabase.resetInstance()
        context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val BaseUserDao = db.baseUserDao()
        val userRoleDao = db.userRoleDao()
        val CallbackSiteMessageDao = db.callbackSiteMessageDao()
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
            val users = BaseUserDao.getAll()
            val userId = users[0].id
            val newMessage = CallbackSiteMessage(
                id = 0, // autoGenerate, поэтому 0

                text = "Тестовое сообщение",
                is_answered = false,
                answer_text = null,

                created_at = System.currentTimeMillis(),
                answered_at = null,

                userId = userId,
                telegram_id = null,

                recipientId = userId
            )

            try {
                CallbackSiteMessageDao.insert(newMessage)
                Log.d("TEST_DB", " New MEss insert ${newMessage} ")
            } catch (e: Exception) {
                Log.e("TEST_DB", "ERROR inserting message: ${e.message}")
            }
            // Чтение всех книг
            val messages = CallbackSiteMessageDao.getAll()
            val messageToUpdate = messages[0].copy(text = "Обновлённая тестовая uькііфаs")
            CallbackSiteMessageDao.update(messageToUpdate)
            Log.d("TEST_DB", "Запись обновлена")
            // Чтение всех книг
            val updatedmessages = CallbackSiteMessageDao.getAll()
            updatedmessages.forEach{
                Log.d("TEST_DB", "ПОсле обновления MESs: ${it.text} ")
                val messageToDelete = updatedmessages[1] // выбираем лекцию для удаления
                CallbackSiteMessageDao.delete(messageToDelete)       //
                Log.d("TEST_DB", "mess удалена")


                val finalmessages = CallbackSiteMessageDao.getAll()
                finalmessages.forEach {
                    Log.d("TEST_DB", "MESSS после удаления: ${it.text} }")
                }
            }
        }
    }
}


