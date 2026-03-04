package com.example.german_server.data.ui.viewModel.user_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
import com.example.german_server.data.dao.UserAvatarDao
import com.example.german_server.data.repository.user_profile.UserProfileRepository
import android.content.SharedPreferences
import com.example.german_server.data.entities.UserAvatar
import com.example.german_server.data.network.models.LeaderboardState
import com.example.german_server.data.network.models.LeaderboardUser
import java.util.UUID

class UserViewModel (private val userDao: BaseUserDao,
                     private val avatarDao: UserAvatarDao,
                     private val profileRepository: UserProfileRepository,
                     private val prefs: SharedPreferences
): ViewModel() {



    private val _currentUser = mutableStateOf<BaseUser?>(null)
    val currentUser: State<BaseUser?> = _currentUser

    private val _galleryAvatars = mutableStateOf<List<String>>(emptyList())
    val galleryAvatars: State<List<String>> = _galleryAvatars

    private val _selectedUser = mutableStateOf<LeaderboardUser?>(null)
    val selectedUser: State<LeaderboardUser?> = _selectedUser

    private val _activeAvatarPath = mutableStateOf<String?>(null)
    val activeAvatarPath: State<String?> = _activeAvatarPath

    private val _serverAvatarPath = mutableStateOf<String?>(null)
    val serverAvatarPath: State<String?> = _serverAvatarPath

    fun selectUser(user: LeaderboardUser) {
        _selectedUser.value = user
    }


    fun setUser(user: BaseUser) {
        _currentUser.value = user
        Log.d("AUTO_USERMODEL", "setUser -> $user in $this")
        user.serverUid?.let { uid -> setCurrentUid(uid) }
    }


    var leaderboardState by mutableStateOf<LeaderboardState?>(null)
        private set

    init {
        Log.d("VM_LIFECYCLE", "UserProfileViewModel CREATED: $this")
        loadAllAvatars()

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


    fun logout() {
        Log.d("AUTO_USERMODEL", "LOGOUT_USER")

        Log.d("LOGOUT_DEBUG", "ДО: _currentUser = ${_currentUser.value}")
        clearCurrentUid()
        _currentUser.value = null
        Log.d("LOGOUT_DEBUG", "ПОСЛЕ: _currentUser = ${_currentUser.value}")
    }

    fun isAuthorized(): Boolean {
        Log.d("AUTO_RISED", "${_currentUser.value}")
        return _currentUser.value != null
    }

    fun decreaseLife() {
        Log.d("USER_DECREASE", "setUser -> ")
        currentUser.value?.let { user ->
            val lifes = user.lifes ?: 0
            if (lifes > 0) {
                Log.d("USER_DECREASE_USER", "setUser -> ${user}")
                val updatedUser = user.copy(lifes = lifes - 1)
                Log.d("USER_DECREASE_LIFES", "setUser -> ${updatedUser}")
                _currentUser.value = updatedUser//user.copy(lifes = lifes - 1)
                saveCurrentUser()
            }
        }
    }

    fun addScore(points: Int) {
        currentUser.value?.let { user ->
            val score = user.score ?: 0
            Log.d("USER_ADDCORE_USER", "setUser -> ${user}")
            val updatedUser = user.copy(score = score + points)
            Log.d("USER_ADDCORE_LIFES", "setUser -> ${updatedUser}")
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
                    Log.d("AUTO_USERMODEL", "loadUserById -> $it")
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
            saveCurrentUser() // запись в базу

            Log.d("AVATAR_UPDATE_VIEW", "Avatar updated to $newPath")

            // 🔥 Дополняем: синхронизация с галереей и активным аватаром
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    if (newPath.isNotEmpty()) {
                        // Деактивируем все предыдущие галерейные аватары
                        avatarDao.deactivateAll(_currentUser.value!!.id)

                        // Активируем текущий
                        avatarDao.activateAvatar(_currentUser.value!!.id, newPath)

                        // Обновляем activeAvatarPath для UI
                        _activeAvatarPath.value = newPath
                        Log.d("AVATAR_UPDATE_VIEW_ACTIVE", "Active avatar path set: $newPath")

                        // Обновляем users.avatarPath в базе
                        userDao.updateAvatarPath(_currentUser.value!!.id, newPath)
                    } else {
                        // Если сброс
                        avatarDao.deactivateAll(_currentUser.value!!.id)
                        _activeAvatarPath.value = null
                        userDao.updateAvatarPath(_currentUser.value!!.id, null)
                        Log.d("AVATAR_UPDATE_VIEW_ACTIVE", "Avatar cleared")
                    }
                } catch (e: Exception) {
                    Log.e("AVATAR_UPDATE_VIEW_ERR", "Error updating avatar", e)
                }
            }
        }
    }
    fun updateAvatarName(newName: String) {
        Log.d("AVATAR_NAmE_UPDATE", "Avatar updated to $newName")
        val now = System.currentTimeMillis()
        currentUser.value?.let { user ->
            val updatedUser = user.copy(
                avatarName = newName,
                avatarLastChanged = now
            )
            _currentUser.value = updatedUser
            saveCurrentUser() // уже в ней есть корутина для записи в базу
            Log.d("AVATAR_Name_UPDATE_1", "Avatar updated to $_currentUser")
        }
    }


    fun updateAvatarPath(filePath: String?) {
        Log.d("AVATAR_PATH_UPDATE", "Avatar updated to $filePath")

        _currentUser.value = _currentUser.value?.copy(avatarPath = filePath)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (filePath != null) {
                    val fileName = File(filePath).name
                    Log.d("AVATAR_NAME_EXTRACT", "Extracted avatarName: $fileName")

                    // 1️⃣ Обновляем users.avatarPath полным путем
                    userDao.updateAvatarPath(_currentUser.value!!.id, filePath)

                    // 2️⃣ Деактивируем все предыдущие
                    avatarDao.deactivateAll(_currentUser.value!!.id)

                    // 3️⃣ Активируем новый
                    avatarDao.activateAvatar(_currentUser.value!!.id, filePath)

                    // 4️⃣ Сразу обновляем activeAvatarPath для UI
                    _activeAvatarPath.value = filePath
                    Log.d("AVATAR_PATH_UPDATE_ACTIVE", "Active avatar path set: $filePath")
                } else {
                    // Если сброс
                    userDao.updateAvatarPath(_currentUser.value!!.id, null)
                    _activeAvatarPath.value = null
                }
            } catch (e: Exception) {
                Log.e("AVATAR_PATH_UPDATE_ERR", "Error updating avatar", e)
            }
        }
    }
    fun uploadServerAvatar(path: String) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val file = File(path)
            val success = profileRepository.uploadGalleryAvatar(file, user)
            if (success) {
                Log.d("AVATAR_UPLOAD", "Галерейный аватар загружен на сервер")
            }
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
        Log.d("SHOCK_MOD", "Серия")
        currentUser.value?.let { user ->
            Log.d("SHOCK_MOD", "BEFORE startOfDay")

            val now = System.currentTimeMillis()
            Log.d("SHOCK_MOD", "now = $now")
            val today = System.currentTimeMillis().startOfDay() // timestamp начала сегодняшнего дня
            val lastUpdate = user.shockmodNow ?: 0L
            val lastUpdateDay = lastUpdate.startOfDay()
            Log.d("SHOCK_MOD", "${user.shockmodNow}  - ${lastUpdateDay} = ${today}")
            val updatedUser = when {

                lastUpdateDay == today -> {
                    Log.d("SHOCK_MOD", "Ветка: УЖЕ ОБНОВЛЯЛИ СЕГОДНЯ")
                    user
                }

                lastUpdateDay == today - 1 * 24 * 60 * 60 * 1000 -> {
                    Log.d("SHOCK_MOD", "Ветка: ПРОДОЛЖАЕМ СЕРИЮ")
                    Log.d("SHOCK_MOD", "Дальше")
                    user.copy(
                        shockmodLong = (user.shockmodLong ?: 0) + 1,
                        shockmodNow = today
                    )

                }

                else -> {
                    Log.d("SHOCK_MOD", "Ветка: СБРОС СЕРИИ")
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


    fun saveAvatarToInternalStorage(context: Context, uri: Uri, type: String): String? {
        val user = currentUser.value
        if (user == null) {
            Log.e("AVATAR_SAVE", "currentUser отсутствует, сохранение прервано")
            return null
        }

        return try {
            val avatarsDir = File(context.filesDir, "avatars")
            if (!avatarsDir.exists()) {
                avatarsDir.mkdirs()
                Log.d("AVATAR_SAVE", "Создана папка для аватаров: ${avatarsDir.absolutePath}")
            } else {
                Log.d(
                    "AVATAR_SAVE",
                    "Папка для аватаров уже существует: ${avatarsDir.absolutePath}"
                )
            }

            // Генерация уникального имени файла
            val uniqueId =
                System.currentTimeMillis().toString() + "_" + UUID.randomUUID().toString().take(8)
            val filename = if (type == "gallery") {
                "avatar_user_${user.id}_$uniqueId.png"
            } else if (type == "server") {
                "server_avatar_user_${user.id}.png"
            } else {
                "avatar_default.png" // на случай других значений type
            }
            val destFile = File(avatarsDir, filename)
            Log.d(
                "AVATAR_SAVE",
                "Файл будет сохранён: ${destFile.absolutePath}, существует ли уже: ${destFile.exists()}"
            )

            context.contentResolver.openInputStream(uri).use { inputStream ->
                if (inputStream == null) {
                    Log.e("AVATAR_SAVE", "Не удалось открыть InputStream для uri: $uri")
                    return null
                }
                destFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            Log.d("AVATAR_SAVE", "Файл успешно сохранён: ${destFile.absolutePath}")
            destFile.absolutePath
        } catch (e: Exception) {
            Log.e("AVATAR_SAVE", "Ошибка при сохранении аватара", e)
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
            Log.d("SYNC_VM", "${user}")
            if (user == null) {
                Log.e("SYNC_VM", "❌ Нет UID")
                return@launch
            }

            val request = profileRepository.createSyncRequest(user)
            Log.d("SYNC_VM", " req  ${request}")
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

    fun syncAvatarIfNeeded() {
        viewModelScope.launch {
            Log.d("SYNC_AVATAR", "✅ avatar sync")

            val currentUid = getCurrentUid()
            Log.d("SYNC_AVATAR", "✅ currentUid из SharedPreferences: $currentUid")

            val user = if (currentUid != null) {
                userDao.getByServerUid(currentUid)
            } else {
                null
            }

            if (user == null) {
                Log.e("SYNC_AVATAR", "❌ Нет UID")
                return@launch
            }
            val request = profileRepository.createUploadAvatarRequest(user)
            Log.d("SYNC_AVATAR", "❌ req  ${request}")
            if (request == null) {
                Log.e("SYNC_AVATAR", "❌ Не удалось создать запрос")
                return@launch
            }
            // Отправляем аватар на сервер только если локальный новее
            val success = profileRepository.uploadAvatar(request)

            if (success) {
                Log.d("SYNC_AVATAR", "✅ Аватар успешно отправлен на сервер")
                // Ничего больше не делаем — серверные данные нас не интересуют
            } else {
                Log.e("SYNC_AVATAR", "❌ Ошибка отправки аватара")
            }
        }
    }

    fun compareWithServerProfile() {
        viewModelScope.launch {
            Log.d("SYNC_COMPARE", "✅ Compsre")
            val localUser = _currentUser.value ?: return@launch
            Log.d("SYNC_COMPARE", "✅ ${localUser}")
            Log.d("SYNC_COMPARE", "✅ ${localUser.email}")

            val serverProfile = profileRepository.loadProfileFromServer(
                localUser.email,
                token = "Token ${localUser.loginToken}"
            )
            Log.d("SYNC_COMPARE", "✅ server profile ${serverProfile}")

            if (serverProfile == null || localUser == null) return@launch

            val serverDate = serverProfile.shockmodNow ?: 0L
            val localDate = localUser.shockmodNow ?: 0L
            Log.d("SYNC_COMPARE", "✅ serverdata ${serverDate}")
            Log.d("SYNC_COMPARE", "✅ local data ${localDate}")
            Log.d("SYNC_COMPARE", "✅ local data ${localDate >= serverDate}")
            val serverAvatarDate = serverProfile.avatarLastChanged ?: 0L
            val localAvatarDate = localUser.avatarLastChanged ?: 0L
            Log.d("SYNC_COMPARE", "✅ serverAVA ${serverAvatarDate}")
            Log.d("SYNC_COMPARE", "✅ local AVA ${localAvatarDate}")
            Log.d("SYNC_COMPARE", "✅ local q ${localAvatarDate >= serverAvatarDate}")
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
            when {
                localAvatarDate > serverAvatarDate -> {
                    Log.d("SYNC_AVATAR", "✅ AVATAR change  ")
                    syncAvatarIfNeeded()
                }

                localAvatarDate >= serverAvatarDate -> {
                    // Локальные данные нов
                    Log.d("SYNC_AVATAR", "✅ AVATAR - not ")

                }
            }
        }
    }

    fun loadLeaderboard() {
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

    fun saveGalleryAvatar(path: String) {
        val user = _currentUser.value ?: run {
            Log.e("AVATAR_ADD", "currentUser отсутствует")
            return
        }

        viewModelScope.launch {
            try {
                Log.d("AVATAR_ADD", "Сохраняем галерейный аватар для userId=${user.id}, путь=$path")

                // 1️⃣ Деактивируем все аватары пользователя
                avatarDao.deactivateAll(user.id)
                Log.d("AVATAR_ADD", "Все аватары пользователя ${user.id} деактивированы")

                // 2️⃣ Вставляем новый аватар в Room
                val newAvatar = UserAvatar(
                    userId = user.id,
                    path = path,
                    isActive = true
                )
                val newId = avatarDao.insertAvatar(newAvatar)
                Log.d("AVATAR_ADD", "Новый аватар вставлен: id=$newId, path=$path")

                // 3️⃣ Обновляем текущего пользователя в памяти
                _currentUser.value = _currentUser.value?.copy(avatarPath = path)
                Log.d(
                    "AVATAR_ADD",
                    "Текущий пользователь обновлён с новым аватаром: ${_currentUser.value}"
                )

            } catch (e: Exception) {
                Log.e("AVATAR_ADD", "Ошибка при сохранении галерейного аватара", e)
            }
        }
    }
    fun saveServerAvatar(path: String) {
        val user = _currentUser.value ?: run {
            Log.e("AVATAR_Server_ADD", "currentUser отсутствует")
            return
        }

        viewModelScope.launch {
            try {
                Log.d("AVATAR_Server_ADD", "Сохраняем галерейный аватар для userId=${user.id}, путь=$path")

                // 2️⃣ Вставляем новый аватар в Room
                val newAvatar = UserAvatar(
                    userId = user.id,
                    path = path,
                    isActive = false
                )
                val newId = avatarDao.insertAvatar(newAvatar)
                Log.d("AVATAR_Server_ADD", "Новый аватар вставлен: id=$newId, path=$path")

                // 3️⃣ Обновляем текущего пользователя в памяти
                _currentUser.value = _currentUser.value?.copy(avatarPath = path)
                Log.d(
                    "AVATAR_Server_ADD",
                    "Текущий пользователь обновлён с новым аватаром: ${_currentUser.value}"
                )

            } catch (e: Exception) {
                Log.e("AVATAR_Server_ADD", "Ошибка при сохранении галерейного аватара", e)
            }
        }
    }
    fun loadAllAvatars(): State<List<String>> {
        val userId = _currentUser.value?.id ?: return galleryAvatars
        Log.d("AVATAR_LOAD", "loadAllAvatars: userId=$userId")
        viewModelScope.launch {
            try {
                val avatarsFromDb = avatarDao.getUserAvatars(userId).map { it.path }
                Log.d("AVATAR_LOAD", "Loaded avatars: $avatarsFromDb")
                _galleryAvatars.value = avatarsFromDb
            } catch (e: Exception) {
                Log.e("AVATAR_LOAD", "Error loading avatars", e)
            }
        }
        return galleryAvatars
    }


    fun deleteGalleryAvatar(avatarPath: String) {
        val userId = _currentUser.value?.id ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("AVATAR_DELETE", "Deleting avatar: $avatarPath")

                val avatar = avatarDao.getAvatarByPath(userId, avatarPath)

                if (avatar != null) {

                    val wasActive = avatar.isActive
                    Log.d("AVATAR_DELETE", "Was active: $wasActive")

                    // 1️⃣ удалить файл
                    val fileDeleted = File(avatarPath).delete()
                    Log.d("AVATAR_DELETE", "File deleted: $fileDeleted")

                    // 2️⃣ удалить запись
                    avatarDao.deleteAvatar(avatar.id)

                    // 3️⃣ если был активным — ОБНУЛИТЬ ВСЁ
                    if (wasActive) {

                        Log.d("AVATAR_DELETE", "Active avatar removed → clearing states")

                        // обновляем users.avatarPath
                        userDao.updateAvatarPath(userId, null)

                        // обновляем ViewModel state
                        _activeAvatarPath.value = null

                        _currentUser.value =
                            _currentUser.value?.copy(avatarPath = null)

                        Log.d("AVATAR_DELETE", "All avatar states cleared")
                    }
                }

            } catch (e: Exception) {
                Log.e("AVATAR_DELETE", "Ошибка удаления", e)
            }
        }
    }
    fun loadActiveAvatar() {
        val userId = _currentUser.value?.id ?: return
        Log.d("AVATAR_ACTIVE", "Loading active avatar for userId=$userId")

        viewModelScope.launch {
            try {
                val avatar = avatarDao.getActiveAvatar(userId)
                Log.d("AVATAR_ACTIVE", "DB active avatar: $avatar")

                if (avatar != null) {
                    _activeAvatarPath.value = avatar.path
                    Log.d("AVATAR_ACTIVE", "Active avatar path set: ${avatar.path}")

                    // 🔥 синхронизируем users.avatarPath
                    userDao.updateAvatarPath(userId, avatar.path)
                    Log.d("AVATAR_ACTIVE", "users.avatarPath updated")
                } else {
                    Log.d("AVATAR_ACTIVE", "No active avatar found")

                    _activeAvatarPath.value = null

                    // 🔥 ОБНУЛЯЕМ avatarPath
                    userDao.updateAvatarPath(userId, null)
                    Log.d("AVATAR_ACTIVE", "users.avatarPath cleared")
                }

            } catch (e: Exception) {
                Log.e("AVATAR_ACTIVE", "Error loading avatars", e)
            }
        }
    }
    fun loadServerAvatar() {
        val userId = _currentUser.value?.id ?: return
        Log.d("AVATAR_SERVER", "Loading server avatar for userId=$userId")

        viewModelScope.launch {
            try {

                val avatar = avatarDao.getServerAvatar(userId)
//                val allAvatars = avatarDao.getUserAvatars(userId)
//                val avatar = allAvatars.firstOrNull { it.path.contains("server_avatar") }
                Log.d("AVATAR_SERVER", "DB active avatar: $avatar")

                if (avatar != null) {
                    _serverAvatarPath.value = avatar.path
                    Log.d("AVATAR_SERVER", "Server avatar path set: ${avatar.path}")

                    // 🔥 синхронизируем users.avatarPath

                } else {
                    Log.d("AVATAR_SERVER", "No server avatar found")

                    _serverAvatarPath.value = null

                    // 🔥 ОБНУЛЯЕМ avatarPath
                    userDao.updateAvatarPath(userId, null)
                    Log.d("AVATAR_ACTIVE", "users.avatarPath cleared")
                }

            } catch (e: Exception) {
                Log.e("AVATAR_SERVER", "Error loading avatars", e)
            }
        }
    }
    fun deactivateAllAvatars() {
        val userId = _currentUser.value?.id ?: return
        viewModelScope.launch(Dispatchers.IO) {
            try {
                avatarDao.deactivateAll(userId)
                Log.d("AVATAR_VM", "Все галерейные аватары деактивированы для userId=$userId")
            } catch (e: Exception) {
                Log.e("AVATAR_VM", "Ошибка деактивации аватаров", e)
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













