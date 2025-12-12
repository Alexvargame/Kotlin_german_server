package com.example.german.data.ui.autorization

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.example.german.data.AppDatabase


class AutorizationViewModel(private val db: AppDatabase) : ViewModel() {

    // Состояние результата логина: true = успешный, false = ошибка, null = ещё не пытались
    private val _loginResult = mutableStateOf<Boolean?>(null)
    val loginResult: State<Boolean?> = _loginResult

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun login(loginOrEmail: String, password: String) {
        viewModelScope.launch {
            Log.d("AUTO_VIEWMODEL", "aoturiz called with username=$loginOrEmail")
            val user = withContext(Dispatchers.IO) {
                db.baseUserDao().getUser(loginOrEmail, password)
            }
            if (user != null) {
                Log.d("AUTO_VIEWMODEL", "auotriz called with username=$user")
                _loginResult.value = true
                _errorMessage.value = ""
            } else {
                _loginResult.value = false
                _errorMessage.value = "Неверный логин или пароль"
            }
        }
    }
}
