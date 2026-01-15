package com.example.german_server.data.ui.viewModel.autorization

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.Context

import com.example.german_server.data.AppDatabase
import com.example.german_server.data.entities.BaseUser

class AutorizationViewModel(private val db: AppDatabase) : ViewModel() {

    // Состояние результата логина: true = успешный, false = ошибка, null = ещё не пытались
    private val _loginResult = mutableStateOf<BaseUser?>(null)
    val loginResult: State<BaseUser?> = _loginResult

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun login(context: Context, loginOrEmail: String, password: String) {
        viewModelScope.launch {
            Log.d("AUTO_VIEWMODEL", "aoturiz called with username=$loginOrEmail")
            val user = withContext(Dispatchers.IO) {
                db.baseUserDao().getUser(loginOrEmail, password)
            }
            if (user != null) {
                Log.d("AUTO_VIEWMODEL", "auotriz called with username=$user")
                _loginResult.value = user
                //_errorMessage.value = ""
                // === ДОБАВЛЯЕМ СОХРАНЕНИЕ АВТОРИЗАЦИИ ===
                saveUserLoggedInStatus(context,user)
                Log.d("AUTO_VIEWMODEL_CHECK", "aoturiz called with username=${saveUserLoggedInStatus(context,user)}")

            } else {
                //_loginResult.value = false
                _errorMessage.value = "Неверный логин или пароль"
            }
        }
    }
    // === Внутри AutorizationViewModel ===
    private fun saveUserLoggedInStatus(context: Context, user: BaseUser) {
        // SharedPreferences сохраняем флаг и ID пользователя
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("is_logged_in", true)          // пользователь авторизован
            putString("user_id", user.id.toString()) // сохраняем ID
            apply()                                   // сохраняем изменения
        }
    }
    fun checkUserLoggedIn(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("is_logged_in", false)
        val userId = sharedPref.getString("user_id", null)
        Log.d("AUTO_VIEWMODEL_CHECK", "checkUserLoggedIn: isLoggedIn=$isLoggedIn, userId=$userId")
        return isLoggedIn && userId != null
    }
    fun getLoggedInUserId(context: Context): String? {
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val containsKey = sharedPref.contains("user_id")
        Log.d("AUTO_VIEWMODEL_CHECK", "getLoggedInUserId: contains 'user_id'? $containsKey")
        Log.d("AUTO_VIEWMODEL_CHECK", "getLoggedInUserId: contains 'user_id'? $sharedPref")
        return if (containsKey) {
            Log.d("AUTO_VIEWMODEL_CHECK", "return")
            val id = sharedPref.getString("user_id", null)
            Log.d("AUTO_VIEWMODEL_CHECK", "getLoggedInUserId: raw id from SharedPreferences = $id")
            val validId = id.takeIf { it != null }
            Log.d("AUTO_VIEWMODEL_CHECK", "getLoggedInUserId: returning valid id = $validId")
            validId
        } else {
            Log.d("AUTO_VIEWMODEL_CHECK", "getLoggedInUserId: 'user_id' отсутствует")
            null
        }
    }
    fun logout(context: Context) {
        Log.d("AUTO_USERMODEL","LOGOUT_AUTO")
        val sharedPref = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPref.edit()
            .remove("is_logged_in")
            .remove("user_id")
            .apply()

        _loginResult.value = null
    }


}
