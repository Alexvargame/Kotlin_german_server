package com.example.german_server.data.ui.viewModel.registration


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.german_server.data.repository.registraiton.UserRegistrationRepository
import kotlinx.coroutines.launch
import android.database.sqlite.SQLiteConstraintException

import android.util.Log

import com.example.german_server.data.entities.BaseUser
import com.example.german_server.data.network.models.RegisterRequest
import com.example.german_server.data.network.models.RegisterResponse
import com.example.german_server.data.network.RetrofitClient


class RegistrationViewModel(private val repo: UserRegistrationRepository) : ViewModel() {


    var registrationResult = mutableStateOf<BaseUser?>(null)
        private set
    var errorMessage = mutableStateOf("")
        private set

    fun registerUser(email: String, username: String, password: String) {

        val request = RegisterRequest(email = email, username = username, password = password)
        RetrofitClient.apiService.registerUser(request)
            .enqueue(object: Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let { registerResponse ->

                            // 3️⃣ Передаём серверные UID и token в Repository
                            viewModelScope.launch {
                                try {
                                    val newUser = repo.registerUser(
                                        email = email,
                                        username = username,
                                        password = password,
                                        serverUid = registerResponse.uid,
                                        loginToken = registerResponse.login_token
                                    )

                                    // 4️⃣ Отдаём результат в UI
                                    registrationResult.value = newUser
                                    errorMessage.value = ""

                                } catch (e: SQLiteConstraintException) {
                                    registrationResult.value = null
                                    errorMessage.value = "Пользователь с таким email или логином уже существует"
                                } catch (e: Exception) {
                                    registrationResult.value = null
                                    errorMessage.value = "Ошибка регистрации: ${e.message}"
                                }
                            }
                        }
                    } else {
                        // 5️⃣ Ошибка сервера
                        errorMessage.value = "Ошибка сервера: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    // 6️⃣ Ошибка сети
                    errorMessage.value = "Нет соединения с сервером"
                }
            })


            /*viewModelScope.launch {
            Log.d("REG_VIEWMODEL", "registerUser called with username=$username")
            try {
                val newUser = repo.registerUser(email, username, password) // EMAIL  <- Repository создаёт BaseUser
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
        }*/
    }
}
