package com.example.german_server.data.ui.viewModel.user_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Context
import android.net.Uri
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar
import java.time.Instant
import java.util.concurrent.TimeUnit
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlin.math.max

import com.example.german_server.data.entities.BaseUser
import com.example.german_server.data.dao.BaseUserDao
import com.example.german_server.data.repository.user_profile.UserProfileRepository
import android.content.SharedPreferences
import com.example.german_server.data.network.models.LeaderboardState
import com.example.german_server.data.network.models.LeaderboardUser
import java.util.TimeZone

class UserViewModel (private val userDao: BaseUserDao,
                     private val profileRepository: UserProfileRepository,
                     private val prefs: SharedPreferences
): ViewModel() {

    private val _currentUser = mutableStateOf<BaseUser?>(null)
    val currentUser: State<BaseUser?> = _currentUser


    private val _selectedUser = mutableStateOf<LeaderboardUser?>(null)
    val selectedUser: State<LeaderboardUser?> = _selectedUser

    fun selectUser(user: LeaderboardUser) {
        _selectedUser.value = user
    }

    var leaderboardState by mutableStateOf<LeaderboardState?>(null)
        private set
    init {
        Log.d("VM_LIFECYCLE", "UserProfileViewModel CREATED: $this")
    }

    private fun getCurrentUid(): String? {
        return prefs.getString("current_uid", null)
    }

    private fun setCurrentUid(uid: String) {
        prefs.edit().putString("current_uid", uid).apply()
    }

    private fun clearCurrentUid() {
        prefs.edit().remove("current_uid").apply()
    }

    fun setUser(user: BaseUser) {
        _currentUser.value = user
        Log.d("AUTO_USERMODEL","setUser -> $user in $this")
        user.serverUid?.let { uid -> setCurrentUid(uid) }
    }

    fun logout() {
        Log.d("AUTO_USERMODEL","LOGOUT_USER")

        Log.d("LOGOUT_DEBUG", "ДО: _currentUser = ${_currentUser.value}")
        clearCurrentUid()
        _currentUser.value = null
        Log.d("LOGOUT_DEBUG", "ПОСЛЕ: _currentUser = ${_currentUser.value}")
    }

    fun isAuthorized(): Boolean {
        Log.d("AUTO_RISED","${_currentUser.value}")
        return _currentUser.value != null
    }
    fun decreaseLife() {
        Log.d("USER_DECREASE","setUser -> ")
        currentUser.value?.let { user ->
            val lifes = user.lifes ?: 0
            if (lifes > 0) {
                Log.d("USER_DECREASE_USER","setUser -> ${user}")
                val updatedUser = user.copy(lifes = lifes - 1)
                Log.d("USER_DECREASE_LIFES","setUser -> ${updatedUser}")
                _currentUser.value = updatedUser//user.copy(lifes = lifes - 1)
                saveCurrentUser()
            }
        }
    }

    fun addScore(points: Int) {
        currentUser.value?.let { user ->
            val score = user.score ?: 0
            Log.d("USER_ADDCORE_USER","setUser -> ${user}")
            val updatedUser = user.copy(score = score + points)
            Log.d("USER_ADDCORE_LIFES","setUser -> ${updatedUser}")
            _currentUser.value = updatedUser //user.copy(score = score + points)
            saveCurrentUser()
        }
    }
    private fun saveCurrentUser() {
        currentUser.value?.let { user ->
            viewModelScope.launch {
                try {
                    userDao.update(user)
                    Log.d("USER_DB_SAVE", "User saved: $user")
                } catch (e: Exception) {
                    Log.e("USER_DB_SAVE", "Error saving user", e)
                }
            }
        }
    }

    private fun saveUserToDatabase(user: BaseUser) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insert(user)  // Сохраняем или обновляем пользователя в базе
        }
    }
    fun loadUserById(userId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userFromDb = userDao.getById(userId)
                userFromDb?.let {
                    // Перекладываем в main thread, чтобы Compose увидел изменения
                    _currentUser.value = it
                    Log.d("AUTO_USERMODEL","loadUserById -> $it")
                    compareWithServerProfile()
                }
            } catch (e: Exception) {
                Log.e("AUTO_USERMODEL", "Error loading user by ID", e)
            }
        }
    }
    fun updateAvatar(newPath: String) {
        currentUser.value?.let { user ->
            val updatedUser = user.copy(avatarPath = newPath)
            _currentUser.value = updatedUser
            saveCurrentUser() // уже в ней есть корутина для записи в базу
            Log.d("AVATAR_UPDATE", "Avatar updated to $newPath")
        }
    }
    fun updateAvatarName(newName: String) {
        currentUser.value?.let { user ->
            val updatedUser = user.copy(avatarName = newName)
            _currentUser.value = updatedUser
            saveCurrentUser() // уже в ней есть корутина для записи в базу
            Log.d("AVATAR_NMAE_UPDATE", "Avatar updated to $newName")
        }
    }
    fun updateUser(
        email: String,
        username: String,
        phone: String? = null,
        telegram: String? = null,
        botPass: String? = null
    ) {
        _currentUser.value?.let { user ->
            val updatedUser = user.copy(
                email = email,
                username = username,
                phone = phone,
                telegram_username = telegram,
                user_bot_pass = botPass
            )
            _currentUser.value = updatedUser
            saveCurrentUser() // private функция внутри ViewModel
        }
    }

    fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
    fun parseIsoToLong(dateString: String): Long {
        return try {
            Instant.parse(dateString).toEpochMilli()
        } catch (e: Exception) {
            0L
        }
    }
    fun getAvatarPath(): String? {
        return currentUser.value?.avatarPath
    }
    fun updateShockMod() {
        currentUser.value?.let { user ->
            val today = System.currentTimeMillis().startOfDay() // timestamp начала сегодняшнего дня
            val lastUpdate = user.shockmodNow ?: 0L
            val lastUpdateDay = lastUpdate.startOfDay()

            val updatedUser = when {
                lastUpdateDay == today -> {
                    // Уже обновляли сегодня → ничего не меняем
                    user
                }
                lastUpdateDay == today - 1 * 24 * 60 * 60 * 1000 -> {
                    // Продолжаем серию
                    user.copy(
                        shockmodLong = (user.shockmodLong ?: 0) + 1,
                        shockmodNow = today
                    )
                }
                else -> {
                    // Сброс серии
                    user.copy(
                        shockmodBegin = today,
                        shockmodNow = today,
                        shockmodLong = 1
                    )
                }
            }

            _currentUser.value = updatedUser
            saveCurrentUser() // сохраняем изменения в Room
        }
    }

    fun saveAvatarToInternalStorage(context: Context, uri: Uri): String? {
        return try {
            val avatarsDir = File(context.filesDir, "avatars")
            if (!avatarsDir.exists()) avatarsDir.mkdirs()

            val filename = "avatar_user_${currentUser.value?.id ?: System.currentTimeMillis()}.png"
            val destFile = File(avatarsDir, filename)

            context.contentResolver.openInputStream(uri).use { inputStream ->
                destFile.outputStream().use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }
            }

            destFile.absolutePath
        } catch (e: Exception) {
            Log.e("USER_AVATAR_SAVE", "Error saving avatar", e)
            null
        }
    }
    fun setServerData(uid: String, token: String) {
        currentUser.value?.let { user ->
            val updatedUser = user.copy(serverUid = uid, loginToken = token)
            _currentUser.value = updatedUser
            saveCurrentUser() // уже сохраняет в Room через DAO
        }

    }

    fun getDaysLeft(user: BaseUser): Int {
        val daysPassed = TimeUnit.MILLISECONDS.toDays(
            System.currentTimeMillis() - user.registration_date
        ).toInt()
        return max(0, 7 - daysPassed)
    }
    fun resendVerification(email: String) {
        viewModelScope.launch {
            val success = profileRepository.resendVerificationEmail(email)
            // Можно обновить UI состояние (например, показать Snackbar)
            if (success) {
                Log.d("USER_VIEWMODEL", "Запрос на отправку письма выполнен")
            }
        }
    }
    // UserViewModel.kt
    fun deleteAccount(uid: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch { // Используем встроенный viewModelScope
            Log.e("DELETE_ACCOUNT_MODEL", "Enter_ model")
            val success = profileRepository.deleteAccount(uid)
            if (success) {
                userDao.deleteByServerUid(uid)
            }
            Log.e("DELETE_ACCOUNT_MODEL", "${success}")
            onResult(success)
        }
    }
    fun syncProgressAfterLesson() {
        viewModelScope.launch {
            Log.d("SYNC_PROGRESS", "✅ progress")

            val currentUid = getCurrentUid()
            Log.d("SYNC_PROGRESS", "✅ currentUid из SharedPreferences: $currentUid")

            // 2. Ищем пользователя в базе по UID
            val user = if (currentUid != null) {
                userDao.getByServerUid(currentUid)
            } else {
                null
            }

            if (user == null) {
                Log.e("SYNC_VM", "❌ Нет UID")
                return@launch
            }

            val request = profileRepository.createSyncRequest(user)
            Log.d("SYNC_VM", "❌ req  ${request}")
            if (request == null) {
                Log.e("SYNC_VM", "❌ Не удалось создать запрос")
                return@launch
            }
            val success = profileRepository.syncProgress(request)

            if (success) {
                Log.d("SYNC_VM", "✅ Синхронизация успешна")
            } else {
                Log.e("SYNC_VM", "❌ Ошибка синхронизации")
            }
        }
    }
    fun compareWithServerProfile() {
        viewModelScope.launch {
            Log.d("SYNC_COMPARE", "✅ Compsre")
            val localUser = _currentUser.value ?: return@launch
            Log.d("SYNC_COMPARE", "✅ ${localUser}")
            Log.d("SYNC_COMPARE", "✅ ${localUser.email}")
            val serverProfile = profileRepository.loadProfileFromServer(localUser.email,  token = "Token ${localUser.loginToken}"  )
            Log.d("SYNC_COMPARE", "✅ server profile ${serverProfile}")

            if (serverProfile == null || localUser == null) return@launch

            val serverDate = serverProfile.shockmodNow ?: 0L
            val localDate = localUser.shockmodNow ?: 0L
            Log.d("SYNC_COMPARE", "✅ serverdata ${serverDate}")
            Log.d("SYNC_COMPARE", "✅ local data ${localDate}")
            Log.d("SYNC_COMPARE", "✅ local data ${localDate >= serverDate}")
            when {
                serverDate > localDate -> {
                    Log.d("SYNC_COMPARE", "✅ server MORE then ")
                    _currentUser.value = localUser.copy(
                        score = serverProfile.score,
                        shockmodLong = serverProfile.streakDays,
                        shockmodNow = serverProfile.shockmodNow
                    )
                    saveCurrentUser()
                }

                localDate >= serverDate -> {
                    // Локальные данные нов
                    Log.d("SYNC_COMPARE", "✅ local _ sync")
                    syncProgressAfterLesson()
                }
            }
        }
    }
    fun loadLeaderboard(){
        viewModelScope.launch {
            val token = currentUser.value?.loginToken ?: return@launch
            try {
                val response = profileRepository.loadRating("Token $token")
                leaderboardState = response?.let {
                    LeaderboardState(
                        scoreTop = it.scoreRating.top,
                        scoreMyRank = it.scoreRating.currentUserRank,
                        streakTop = it.shockmodRating.top,
                        streakMyRank = it.shockmodRating.currentUserRank
                    )
                }
            } catch (e: Exception) {
                // можно логировать
            }
        }
    }

}



fun Long.startOfDay(): Long {
    val cal = Calendar.getInstance().apply { timeInMillis = this@startOfDay }
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.timeInMillis
}













