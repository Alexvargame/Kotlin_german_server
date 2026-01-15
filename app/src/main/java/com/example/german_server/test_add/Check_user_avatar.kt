package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.example.german_server.data.entities.BaseUser

class Check_user_avatar(private val context: Context) {


    fun checkuseravatar() {
   

        Log.d("AVATAR_CHECK", " Context ${context}")
        //AppDatabase.resetInstance()
        //context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("AVATAR_CHECK", "DB path: ${context.getDatabasePath("app.db")}")
        val baseUserDao = db.baseUserDao()

        //BaseUserDao.deleteAll()
        //userRoleDao.deleteAll()
        CoroutineScope(Dispatchers.IO).launch {

            val users = baseUserDao.getAll()
            users.forEach {
                Log.d("AVATAR_CHECK", "USERS: ${it.username} / ${it.email}")
            }
            val userId: Long = 1
            val newAvatarPath = null
            baseUserDao.updateAvatar(userId, newAvatarPath)

            // 3️⃣ Создание нового пользователя с аватаркой
            val newUser = BaseUser(
                password = "1234",
                last_login = null,
                is_superuser = false,
                registration_date = System.currentTimeMillis(),
                name = "Alex",
                surname = "Ivanov",
                username = "alex123",
                email = "alex@mail.com",
                phone = null,
                last_login_date = System.currentTimeMillis(),
                is_active = true,
                is_admin = false,
                user_bot_pass = null,
                userRoleId = 1,
                lifes = 5,
                score = 0,
                last_life_update = System.currentTimeMillis(),
                chat_id = null,
                telegram_username = null,
                user_bot_id = null,
                avatarPath = null
            )
            val users1 = baseUserDao.getAll()
            users1.forEach {
                Log.d("AVATAR_CHECK_after", "USERS: ${it.username} / ${it.email}/${it.avatarPath}")
            }

        }
    }
}


