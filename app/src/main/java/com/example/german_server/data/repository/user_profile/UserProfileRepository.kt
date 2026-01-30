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


}