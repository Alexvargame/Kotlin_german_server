package com.example.german.data.repository.registraiton

import com.example.german.data.dao.UserRegistrationDao

import com.example.german.data.entities.BaseUser
import android.util.Log

class UserRegistrationRepository(private val UserRegistrationDao: UserRegistrationDao) {

    suspend fun registerUser(email: String, username: String, password: String): BaseUser? {
        val now = System.currentTimeMillis()
        val user = BaseUser(
            email = email,
            username = username,
            password = password,
            registration_date = now,
            last_login_date = now,
            last_login = null,
            is_superuser = false,
            is_admin = false,
            is_active = true,
            userRoleId = 1,
            lifes = 5,
            score = 0,
            last_life_update = now,
            name = null,
            surname = null,
            phone = null,
            user_bot_pass = null,
            chat_id = null,
            telegram_username = null,
            user_bot_id = null
        )
        return try {
            Log.e("USER_", "vor insert")
            Log.d("USER_DATA", "username=${user.username}, email=${user.email}, pass=${user.password}")
            UserRegistrationDao.insertUser(user)
            Log.e("USER_", "after insert")
            user  // успешно вставили
        } /*catch (e: SQLiteConstraintException) {
            Log.e("USER_", "false")
            false // пользователь с таким email или username уже есть
        }*/
        catch (e: Exception) {
            Log.e("USER_ERROR", "insert failed: ${e.message}")
            e.printStackTrace()
            null
        }

    }


    suspend fun userExists(email: String, username: String): Boolean {
        return UserRegistrationDao.getUserByEmail(email) != null ||
                UserRegistrationDao.getUserByUsername(username) != null
    }
}
