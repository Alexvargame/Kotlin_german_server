package com.example.german_server.data.repository.user_profile

// ProfileRepository.kt
import android.os.Build
import android.util.Log
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType

import kotlin.Result

import com.example.german_server.data.dao.BaseUserDao
import com.example.german_server.data.dao.UserAvatarDao
import com.example.german_server.data.network.ApiService
import com.example.german_server.data.network.models.ResendVerificationRequest
import com.example.german_server.data.network.models.ProfileResponse
import com.example.german_server.data.network.models.SyncProgressRequest
import com.example.german_server.data.network.models.AvatarUploadRequest

import com.example.german_server.data.entities.BaseUser
import com.example.german_server.data.network.models.FcmTokenRequest
import com.example.german_server.data.network.models.LeaderboardResponse
import com.example.german_server.data.network.models.SenderUser


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

    suspend fun sendFcmToken(fcmToken: String, token: String?) {

        if (token == null) {
            Log.d("FCM", "auth token NULL → не отправляем")
            return
        }

        val deviceName = "${Build.MANUFACTURER} ${Build.MODEL}"

        try {
            Log.d("FCM", "Отправка FCM токена: $fcmToken устройство=$deviceName")

            val response = apiService.saveFcmToken(
                FcmTokenRequest(
                    fcm_token = fcmToken,
                    device_name = deviceName
                ),
                "Token $token"
            )

            Log.d("FCM", "response code=${response.code()}")

            if (response.isSuccessful) {
                Log.d("FCM", "FCM токен успешно сохранён")
            } else {
                Log.d("FCM", "Ошибка сохранения токена code=${response.code()}")
            }

        } catch (e: Exception) {
            Log.e("FCM", "Ошибка отправки FCM токена: ${e.message}", e)
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

    suspend fun getByServerUid(uid: String): BaseUser? {
        return baseUserDao.getByServerUid(uid)
    }

    suspend fun getSender(uid: String, token: String): SenderUser? {
        Log.d("SENDER", "📁 Toekn_ repo: ${token}")
        return try {
            val response = apiService.getSender(uid, "Token $token")
            Log.d("SENDER", "📁 resp: ${response}")
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getAllAdmin(token: String): List<SenderUser>? {
        Log.d("Admins", "📁 Toekn_ repo: ${token}")
        return try {
            val response = apiService.getAllAdmin("Token $token")
            Log.d("Admins", "📁 resp_Senders: ${response} / ${response.body()}")
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getAllSenders(token: String): List<SenderUser>? {
        Log.d("SENDERs", "📁 Toekn_ repo: ${token}")
        return try {
            val response = apiService.getAllSenders("Token $token")
            Log.d("SENDERs", "📁 resp_Senders: ${response} / ${response.body()}")
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }
    suspend fun uploadGalleryAvatar(image: File, user: BaseUser): Boolean {
        return try {

            Log.d("UPLOAD", "🔄 Старт upload")
            Log.d("UPLOAD", "📁 File path: ${image.absolutePath}")
            Log.d("UPLOAD", "📁 File exists: ${image.exists()}")
            Log.d("UPLOAD", "📁 File size: ${image.length()} bytes")

            if (!image.exists()) {
                Log.e("UPLOAD", "❌ Файл не существует")
                return false
            }

            val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())

            val multipartBody = MultipartBody.Part.createFormData(
                name = "image",
                filename = image.name,
                body = requestFile
            )
            val uidPart = user.serverUid.toString().toRequestBody("text/plain".toMediaType())


            Log.d("UPLOAD", "📦 Multipart создан: ${image.name}")

            val token = user.loginToken
            if (token.isNullOrBlank()) {
                Log.e("UPLOAD", "❌ Token пустой")
                return false
            }

            Log.d("UPLOAD", "🔐 Отправляем с Token")

            val response = apiService.uploadGalleryAvatar(
                authorization = "Token $token",
                uid = uidPart,
                image= multipartBody
            )

            Log.d("UPLOAD", "📤 Response code: ${response.code()}")
            Log.d("UPLOAD", "📤 Response headers: ${response.headers()}")

            if (!response.isSuccessful) {
                Log.e("UPLOAD", "❌ HTTP ошибка: ${response.code()}")
                Log.e("UPLOAD", "❌ Error body: ${response.errorBody()?.string()}")
                return false
            }

            val body = response.body()
            if (body == null) {
                Log.e("UPLOAD", "❌ Response body null")
                return false
            }

            Log.d("UPLOAD", "✅ success: ${body.success}")
            Log.d("UPLOAD", "✅ updated: ${body.updated}")
            Log.d("UPLOAD", "✅ message: ${body.message}")
            body.success

        } catch (e: Exception) {
            Log.e("UPLOAD", "🔥 Исключение: ${e.message}", e)
            false
        }
    }
        // Проверка: есть ли задания на сегодня?
        fun hasQuestsForToday(user: BaseUser): Boolean {
            return user.lastQuestReset
        }
//        // Сброс флага lastQuestReset (меняем на противоположный)
//        fun resetLastQuestFlag(user: BaseUser) {
//            val updatedUser = user.copy(lastQuestReset = !user.lastQuestReset)
//            baseUserDao.update(updatedUser)
//        }

    }