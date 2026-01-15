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
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar

import com.example.german_server.data.entities.BaseUser
import com.example.german_server.data.dao.BaseUserDao

class UserViewModel (private val userDao: BaseUserDao): ViewModel() {

    private val _currentUser = mutableStateOf<BaseUser?>(null)
    val currentUser: State<BaseUser?> = _currentUser


    init {
        Log.d("VM_LIFECYCLE", "UserProfileViewModel CREATED: $this")
    }

    fun setUser(user: BaseUser) {
        _currentUser.value = user
        Log.d("AUTO_USERMODEL","setUser -> $user in $this")
    }

    fun logout() {
        Log.d("AUTO_USERMODEL","LOGOUT_USER")
        _currentUser.value = null
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
}


fun Long.startOfDay(): Long {
    val cal = Calendar.getInstance().apply { timeInMillis = this@startOfDay }
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.timeInMillis
}














