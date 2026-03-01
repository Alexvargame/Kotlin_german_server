package com.example.german_server.data.repository.user_profile

// ProfileRepository.kt
import android.util.Log
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import kotlin.Result

import com.example.german_server.data.dao.BaseUserDao
import com.example.german_server.data.dao.UserAvatarDao
import com.example.german_server.data.network.ApiService
import com.example.german_server.data.network.models.ResendVerificationRequest
import com.example.german_server.data.network.models.ProfileResponse
import com.example.german_server.data.network.models.SyncProgressRequest
import com.example.german_server.data.network.models.AvatarUploadRequest

import com.example.german_server.data.entities.BaseUser
import com.example.german_server.data.network.models.LeaderboardResponse


class UserProfileRepository(
    private val apiService: ApiService,
    private val baseUserDao: BaseUserDao,
    private val avatarDao: UserAvatarDao,

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
            lifes = user.lifes,
            shockmodNow = user.shockmodNow ?: System.currentTimeMillis()
        )
    }
    fun createUploadAvatarRequest(user: BaseUser): AvatarUploadRequest? {
        val uid = user.serverUid ?: return null

        return AvatarUploadRequest(
            serverUid = uid,
            avatarName = user.avatarName,
            avatarLastChanged = user.avatarLastChanged ?: 0L
        )
    }
    fun createUploadGalleryAvatarRequest(user: BaseUser): AvatarUploadRequest? {
        val uid = user.serverUid ?: return null

        return AvatarUploadRequest(
            serverUid = uid,
            avatarName = user.avatarName,
            avatarLastChanged = user.avatarLastChanged ?: 0L // либо timestamp локального файла
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
    suspend fun uploadAvatar(request: AvatarUploadRequest): Boolean {
        return try {
            Log.d("SYNC_REPO", "🔄 Отправка аватара на сервер")
            val response = apiService.uploadAvatar(request = request)
            Log.d("SYNC_REPO", "🔄 ${response}")

            if (response.isSuccessful) {
                Log.d("SYNC_REPO", "✅ Аватар отправлен")
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

    suspend fun uploadGalleryAvatar(file: File, user: BaseUser): Boolean {
        return try {
            Log.d("SYNC_REPO_GAL", "🔄 Начало загрузки галерейного аватара: ${file.name}")

            // --- Превращаем файл в RequestBody с MIME типом "image/png" ---
            val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
            Log.d("SYNC_REPO_GAL", "📄 RequestBody создан для файла: ${requestFile}")

            // --- Создаём multipart-часть для Retrofit ---
            val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)
            Log.d("SYNC_REPO_GAL", "📦 MultipartBody создан для Retrofit  - ${multipartBody}")

            // --- Преобразуем serverUid пользователя в RequestBody ---
            val serverUidBody = user.serverUid?.toRequestBody("text/plain".toMediaTypeOrNull())
                ?: run {
                    Log.e("SYNC_REPO_GAL", "❌ serverUid пустой, прерываем загрузку")
                    return false
                }
            Log.d("SYNC_REPO", "🆔 serverUid подготовлен: ${user.serverUid} - ${serverUidBody}")

            val timestampBody = System.currentTimeMillis().toString()
                .toRequestBody("text/plain".toMediaTypeOrNull())
            Log.d("SYNC_REPO_GAL", "⏱ timestamp подготовлен: ${System.currentTimeMillis()}")

            // --- Вызываем Retrofit эндпоинт uploadGalleryAvatar ---
            val response = apiService.uploadGalleryAvatar(
                authorization = "Token ${user.loginToken}",
                file = multipartBody,
                serverUid = serverUidBody,
                avatarLastChanged = timestampBody
            )
            Log.d("SYNC_REPO_GAL", "📤${response}")
            Log.d("SYNC_REPO_GAL", "Status: ${response.code()}")
            Log.d("SYNC_REPO_GAL", "Headers: ${response.headers()}")
            Log.d("SYNC_REPO_GAL", "Body: ${response.errorBody()?.string() ?: response.body()}")
            Log.d("SYNC_REPO_GAL", "📤 Отправка на сервер завершена, код ответа: ${response.code()}")

            // --- Проверяем успешность ответа ---
            if (response.isSuccessful) {
                Log.d("SYNC_REPO_GAL", "✅ Галерейный аватар успешно загружен: ${file.name}")
                true
            } else {
                Log.e("SYNC_REPO_GAL", "❌ Сервер вернул ошибку: ${response.code()} / ${response.message()}")
                false
            }

        } catch (e: Exception) {
            // --- Логируем ошибки сети или исключения ---
            Log.e("SYNC_REPO", "🔥 Исключение при загрузке галерейного аватара: ${e.message}", e)
            false
        }
    }

}