package com.example.german_server.data.ui.viewModel.registration

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.german_server.data.repository.registraiton.UserRegistrationRepository
import kotlinx.coroutines.launch
import android.database.sqlite.SQLiteConstraintException
import com.example.german_server.data.network.models.RegisterRequest
import com.example.german_server.data.network.models.RegisterResponse

import android.util.Log

import com.example.german_server.data.entities.BaseUser
class RegistrationViewModel(private val repo: UserRegistrationRepository) : ViewModel() {


    var registrationResult = mutableStateOf<BaseUser?>(null)
        private set
    var errorMessage = mutableStateOf("")
        private set

    fun registerUser(email: String, username: String, password: String) {
        viewModelScope.launch {
            Log.d("REG_VIEWMODEL", "registerUser called with username=$username")

            try {
                val newUser = repo.registerUser(
                    email,
                    username,
                    password,
                    1L,
                   // serverUid = null,//registerResponse.uid,
                   // loginToken = null,// registerResponse.login_token
                ) // EMAIL  <- Repository создаёт BaseUser
                registrationResult.value = newUser
                Log.d("REG_VIEWMODEL", "registerUser success=$newUser")

                if (newUser != null) {
                    registrationResult.value = newUser
                    errorMessage.value = ""
                } else {
                    registrationResult.value = null
                    errorMessage.value = "Пользователь с таким email или логином уже существует"
                }
            } catch (e: SQLiteConstraintException) {
                registrationResult.value = null
                errorMessage.value = "Пользователь с таким email или логином уже существует"
                Log.e("REG_VIEWMODEL", "SQLiteConstraintException", e)
            }
        }
    }
}
