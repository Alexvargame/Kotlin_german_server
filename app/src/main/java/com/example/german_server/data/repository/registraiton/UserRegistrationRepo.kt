package com.example.german_server.data.repository.registraiton

import com.example.german_server.data.dao.UserRegistrationDao

import com.example.german_server.data.entities.BaseUser
import com.example.german_server.data.dao.BaseUserDao
import com.example.german_server.data.network.ApiService
import com.example.german_server.data.network.models.RegisterRequest
import com.example.german_server.data.network.models.RegisterResponse
import com.example.german_server.data.network.EmailRequest

import android.util.Log
import com.example.german_server.data.dao.VerbDao
import retrofit2.Response



class UserRegistrationRepository(private val UserRegistrationDao: UserRegistrationDao,
                                 private val apiService: ApiService,
                                 private val baseUserDao: BaseUserDao,// добавляем ApiService для работы с сервером
                                  ) {

    suspend fun registerUser(email: String,
                             username: String,
                             password: String,
                             userRoleId: Long = 1L,
                             serverUid: String?,
                             loginToken: String? ): BaseUser? {
        val now = System.currentTimeMillis()
        val request = RegisterRequest(
            email = email,
            username = username,
            password = password
        )

        val response: Response<RegisterResponse>
        try {
            response = apiService.registerUser(request)
        } catch (e: Exception) {
            Log.e("USER_ERROR", "Network error: ${e.message}")
            return null
        }
        // 🔹 5. Сервер отклонил регистрацию (HTTP код != 2xx)
        if (!response.isSuccessful) {
            val errorMsg = response.errorBody()?.string()
            Log.e("USER_ERROR", "Server rejected registration: $errorMsg")
            return null
        }
        // 🔹 6. Сервер не прислал тело ответа
        val registerResponse = response.body() ?: run {
            Log.e("USER_ERROR", "Server returned empty response")
            return null
        }
        val user = BaseUser(
            email = email,
            username = username,
            password = password,
            registration_date = now,
            last_login_date = now,
            last_login = null,
            is_superuser = false,
            is_admin =  userRoleId == 1L,
            is_active = true,
            userRoleId = userRoleId,
            lifes = 5,
            score = 0,
            last_life_update = now,
            name = null,
            surname = null,
            phone = null,
            user_bot_pass = null,
            chat_id = null,
            telegram_username = null,
            user_bot_id = null,
            serverUid = registerResponse.uid,
            loginToken = registerResponse.login_token,
            emailVerified = false,

        )
        return try {
            Log.e("USER_", "vor insert")
            Log.d("USER_DATA", "username=${user.username}, email=${user.email}, pass=${user.password}")
            UserRegistrationDao.insertUser(user)
            Log.e("USER_", "after insert")
            user  // успешно вставили
        }
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
    /**
     * Отложенная / повторная верификация email
     *
     * 1️⃣ Пользователь уже есть локально и email_verified = false
     * 2️⃣ Отправка запроса на сервер для повторной отправки письма
     */
    suspend fun resendVerificationEmail(user: BaseUser): Boolean {
        if (user.emailVerified) return false
        return try {
            val request = EmailRequest(user.email)
            val response = apiService.resendVerificationEmail(request)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("USER_ERROR", "Resend verification email failed: ${e.message}")
            false
        }
    }

    /**
     * Проверка статуса email на сервере
     *
     * Если сервер вернул email_verified = true → обновляем локального пользователя
     */
    suspend fun updateEmailVerified(user: BaseUser): Boolean {
        if (user.emailVerified) return true
        return try {
            val request = EmailRequest(email = user.email)
            val response = apiService.checkEmailStatus(request)
            if (response.isSuccessful && response.body()?.email_verified == true) {
                user.emailVerified = true
                baseUserDao.update(user)
                true
            } else false
        } catch (e: Exception) {
            Log.e("USER_ERROR", "Check email status failed: ${e.message}")
            false
        }
    }

    /**
     * Offline-логика 7 дней
     *
     * Разрешено играть до 7 дней после регистрации, если email не подтверждён
     * @return true если можно играть, false если срок истёк (локальный пользователь удалён)
     */
    suspend fun canPlayOffline(user: BaseUser): Boolean {
        if (user.emailVerified) return true

        val now = System.currentTimeMillis()
        val sevenDaysMillis = 7L * 24 * 60 * 60 * 1000

        return if (now - user.registration_date <= sevenDaysMillis) {
            true
        } else {
            baseUserDao.delete(user)
            false
        }
    }
}
