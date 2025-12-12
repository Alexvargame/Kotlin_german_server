package com.example.german.data.ui.registration

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.german.data.repository.UserRegistrationRepository
import kotlinx.coroutines.launch
import android.database.sqlite.SQLiteConstraintException

import android.util.Log


class RegistrationViewModel(private val repo: UserRegistrationRepository) : ViewModel() {


    var registrationResult = mutableStateOf<Boolean?>(null)
        private set
    var errorMessage = mutableStateOf("")
        private set

    fun registerUser(email: String, username: String, password: String) {
        viewModelScope.launch {
            Log.d("REG_VIEWMODEL", "registerUser called with username=$username")
            try {
                val success = repo.registerUser(email, username, password) // EMAIL  <- Repository создаёт BaseUser
                registrationResult.value = success
                Log.d("REG_VIEWMODEL", "registerUser success=$success")

                if (success) {
                    registrationResult.value = true
                    errorMessage.value = ""
                } else {
                    registrationResult.value = false
                    errorMessage.value = "Пользователь с таким email или логином уже существует"
                }
            } catch (e: SQLiteConstraintException) {
                registrationResult.value = false
                errorMessage.value = "Пользователь с таким email или логином уже существует"
                Log.e("REG_VIEWMODEL", "SQLiteConstraintException", e)
            }
        }
    }
}
