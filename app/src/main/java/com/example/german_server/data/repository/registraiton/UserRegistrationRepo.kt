package com.example.german_server.data.repository.registraiton

import com.example.german_server.data.dao.UserRegistrationDao

import com.example.german_server.data.dao.BaseUserDao
import com.example.german_server.data.network.ApiService
import com.example.german_server.data.network.models.RegisterRequest
import com.example.german_server.data.network.models.RegisterResponse
import com.example.german_server.data.network.models.SyncRequest
import com.example.german_server.data.entities.BaseUser
import android.util.Log
import retrofit2.Response


class UserRegistrationRepository(private val UserRegistrationDao: UserRegistrationDao,
                                 private val baseUserDao: BaseUserDao,
                                 private val apiService: ApiService) {

    private fun isAdminEmail(email: String): Boolean {
        return email == "alex.fito2776@gmail.com"
    }

    suspend fun registerUser(email: String,
                             username: String,
                             password: String,
                             userRoleId: Long = 1L,
                            // serverUid: String?,loginToken: String?
    ): BaseUser? {
        Log.d("REG_REPO", "registerRepo ${username}")
        Log.d("REG_REPO", "registerRepo ${username}  ${email} ${userRoleId}" )
        val now = System.currentTimeMillis()
        val request = RegisterRequest(
            email = email,
            username = username,
            password = password
        )
        Log.d("REG_REPO", "${request}")
        val response: Response<RegisterResponse>
        try {
            Log.d("REG_REPO", "1. Before api call")

            response = apiService.registerUser(request) // если падает здесь, дальше логов не будет
            Log.d("REG_REPO", "2. After api call, response=$response")
        } catch (e: Exception) {
            Log.e("USER_ERROR_REPO1", "Network error: ${e.message}")
            return null
        }
        Log.d("REG_REPO", "response${response}")
//         🔹 5. Сервер отклонил регистрацию (HTTP код != 2xx)
        if (!response.isSuccessful) {
            val errorMsg = response.errorBody()?.string()
            Log.e("USER_ERROR_REPO2", "Server rejected registration: $errorMsg")
            return null
        }

        // 🔹 6. Сервер не прислал тело ответа
        val registerResponse = response.body() ?: run {
            Log.e("USER_ERROR_REPO3", "Server returned empty response")
            return null
        }
        Log.d("REG_REPO", "6. Creating BaseUser")


        val user = BaseUser(
            email = email,
            username = username,
            password = password,
            registration_date = now,
            last_login_date = now,
            last_login = null,
            is_superuser = false,
//            is_admin =  userRoleId == 1L,
            is_admin = isAdminEmail(email),
            userRoleId = if (isAdminEmail(email)) 1L else 2L,
            is_active = true,
//            userRoleId = userRoleId,
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
            emailVerified = false
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

    suspend fun syncUserByEmail(email: String): Boolean {
        return try {
            Log.d("SYNC", "Синхронизация по email: $email")

            val request = SyncRequest(email)
            val response = apiService.syncUser(request)

            if (!response.isSuccessful) {
                Log.e("SYNC", "Ошибка сервера при синхронизации")
                return false
            }

            val syncData = response.body()
            if (syncData == null) {
                Log.e("SYNC", "Пустой ответ при синхронизации")
                return false
            }

            Log.d("SYNC", "Получены данные: uid=${syncData.uid}, verified=${syncData.isVerified}")

            // Ищем локального пользователя по email
            val localUser = baseUserDao.getByEmail(email)

            if (localUser == null) {
                Log.e("SYNC", "Локальный пользователь не найден")
                return false
            }

            // Обновляем ВСЕ поля из ответа сервера
            val updatedUser = localUser.copy(
                serverUid = syncData.uid,
                loginToken = syncData.loginToken,
                emailVerified = syncData.isVerified
            )

            baseUserDao.update(updatedUser)
            Log.d("SYNC", "Локальная запись обновлена")
            true

        } catch (e: Exception) {
            Log.e("SYNC", "Ошибка синхронизации: ${e.message}")
            false
        }
    }
}
