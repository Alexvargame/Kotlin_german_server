package com.example.german_server.data.repository.user_profile

// ProfileRepository.kt
import android.util.Log
import com.example.german_server.data.dao.BaseUserDao
import com.example.german_server.data.network.ApiService
import com.example.german_server.data.network.models.ResendVerificationRequest
import com.example.german_server.data.network.models.ProfileResponse
import com.example.german_server.data.network.models.SyncProgressRequest

import com.example.german_server.data.entities.BaseUser
import com.example.german_server.data.network.models.LeaderboardResponse


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

    suspend fun deleteAccount(uid: String): Boolean {
        Log.e("DELETE_ACCOUNT_REPO", "Enter")
        return try {
            val response = apiService.deleteAccount(uid)
            Log.e("DELETE_ACCOUNT_REPO", "${response}/ ${response.isSuccessful}")
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("DELETE_ACCOUNT", "Network error", e)
            false
        }
    }
    // ✅ 1. ЗАГРУЗКА ПРОФИЛЯ С СЕРВЕРА
    suspend fun loadProfileFromServer(email: String?, token: String?): ProfileResponse?{
        return try {
            Log.d("PROFILE_REPO", "🔄 Загрузка профиля по email: ${email}")
            Log.d("PROFILE_REPO token", "🔄 Загрузка профиля по email: ${token}")

            val response = apiService.getProfile(email, token) //syncProgress(request)

            Log.d("PROFILE_REPO_l", "📡 Ответ сервера: ${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                val profile = response.body()!!
                Log.d("PROFILE_REPO_1", "✅ Профиль получен:, очков: ${profile.score}, streak: ${profile.streakDays}")

                profile // Возвращаем профиль
            } else {
                Log.e("PROFILE_REPO_2", "❌ Ошибка сервера: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("PROFILE_REPO_3", "🔥 Ошибка сети: ${e.message}")
            null
        }
    }
    suspend fun syncProgress(request: SyncProgressRequest): Boolean {
        return try {
            Log.d("SYNC_REPO", "🔄 Отправка прогресса на сервер")
            val response = apiService.syncProgress(request)

            if (response.isSuccessful) {
                Log.d("SYNC_REPO", "✅ Данные отправлены")
                true
            } else {
                Log.e("SYNC_REPO", "❌ Ошибка сервера: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("SYNC_REPO", "🔥 Ошибка сети: ${e.message}")
            false
        }
    }

    fun createSyncRequest(user: BaseUser): SyncProgressRequest? {
        val uid = user.serverUid ?: return null

        return SyncProgressRequest(
            serverUid = uid,
            score = user.score ?: 0,
            shockmodLong = user.shockmodLong,
            shockmodNow = user.shockmodNow ?: System.currentTimeMillis()
        )
    }
    suspend fun loadRating(token: String?): LeaderboardResponse? {
        return try {
            Log.d("LeaderboardRepository", "🔄 Загрузка рейтинга по token: $token")

            val response = apiService.getRating(token)

            Log.d("LeaderboardRepository", "📡 Ответ сервера: ${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                val leaderboard = response.body()!!
                Log.d(
                    "LeaderboardRepository",
                    "✅ Рейтинг получен: scoreTop=${leaderboard.scoreRating.top.size}, " +
                            "streakTop=${leaderboard.shockmodRating.top.size}, " +
                            "currentUserRankScore=${leaderboard.scoreRating.currentUserRank}, " +
                            "currentUserRankStreak=${leaderboard.shockmodRating.currentUserRank}"
                )
                leaderboard
            } else {
                Log.e(
                    "LeaderboardRepository",
                    "❌ Ошибка сервера: ${response.code()}"
                )
                null
            }
        } catch (e: Exception) {
            Log.e(
                "LeaderboardRepository",
                "🔥 Ошибка сети: ${e.message}"
            )
            null
        }
    }

}