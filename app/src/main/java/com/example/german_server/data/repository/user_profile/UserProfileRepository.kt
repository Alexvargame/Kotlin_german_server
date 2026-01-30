package com.example.german_server.data.repository.user_profile

// ProfileRepository.kt
import android.util.Log
import com.example.german_server.data.dao.BaseUserDao
import com.example.german_server.data.network.ApiService
import com.example.german_server.data.network.models.ResendVerificationRequest
import com.example.german_server.data.network.models.SyncRequest


class UserProfileRepository(
    private val apiService: ApiService,
    private val baseUserDao: BaseUserDao
) {

    // Функция для повторной отправки письма (переносим из UserRegistrationRepository)
    suspend fun resendVerificationEmail(email: String): Boolean {
        return try {
            Log.d("PROFILE_REPO", "Повторная отправка письма для $email")
            val request = ResendVerificationRequest(email = email)
            val response = apiService.resendVerification(request)
            Log.d("PROFILE_REPO", "request ${request}")
            Log.d("PROFILE_REPO", "✅response ${response}")
            if (response.isSuccessful) {
                Log.d("PROFILE_REPO", "✅ Письмо отправлено")
                true
            } else {
                Log.e("PROFILE_REPO", "❌ Ошибка сервера: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("PROFILE_REPO", "❌ Ошибка сети: ${e.message}")
            false
        }
    }

    // Функция для синхронизации статуса (переносим из UserRegistrationRepository)
    suspend fun resendSyncUserByEmail(email: String): Boolean {
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