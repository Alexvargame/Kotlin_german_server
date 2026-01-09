package com.example.german.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

import com.example.german.data.ui.viewModel.autorization.AutorizationViewModel
import com.example.german.data.ui.viewModel.registration.RegistrationViewModel

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField

import androidx.navigation.NavController
import com.example.german.data.ui.viewModel.user_profile.UserViewModel
import kotlinx.coroutines.delay


@Composable
fun Start_app_screen(userviewModel: UserViewModel,
                     autoviewModel: AutorizationViewModel,
                     navController: NavController,) {

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val isUserLoggedIn = autoviewModel.checkUserLoggedIn(context)
    Log.d("AUTO_VIEWMODEL_CHECK", "isUserLoggedIn = $isUserLoggedIn")


    if (isUserLoggedIn) {
        Log.d("AUTO_VIEWMODEL_CHECK", "Попытка автологина")
        val userIdString: String? = autoviewModel.getLoggedInUserId(context)
        val userId: Long? = userIdString?.toLongOrNull()

        if (userId != null) {
            try {
                Log.d("AUTO_VIEWMODEL_CHECK", "Вызываем loadUserById для userId = $userId")
                userviewModel.loadUserById(userId) // Unit
                Log.d("AUTO_VIEWMODEL_CHECK", "loadUserById вызван")
            } catch (e: Exception) {
                Log.e("AUTO_VIEWMODEL_CHECK", "Ошибка loadUserById", e)
            }
        } else {
            Log.d("AUTO_VIEWMODEL_CHECK", "userId null, не восстанавливаем")
        }
    } else {
        Log.d("AUTO_VIEWMODEL_CHECK", "Пользователь не авторизован, ждем ручного входа")
    }

    Log.d("AUTO_LOGIN", "После проверки авторизации")

    // Подписка на пользователя (или loginResult для ручного входа)
    val loginResult by if (isUserLoggedIn) {
        Log.d("AUTO_VIEWMODEL_CHECK", "Подписываемся на userviewModel.currentUser")
        userviewModel.currentUser
    } else {
        Log.d("AUTO_VIEWMODEL_CHECK", "Подписываемся на autoviewModel.loginResult")
        autoviewModel.loginResult
    }

    val errorMessage by autoviewModel.errorMessage

    // LaunchedEffect реагирует на появление пользователя
    LaunchedEffect(loginResult) {
        loginResult?.let { user ->
            Log.d("AUTO_VIEWMODEL_CHECK", "loginResult не null, устанавливаем пользователя: $user")
            userviewModel.setUser(user)
            delay(1)
            Log.d("AUTO_VIEWMODEL_CHECK", "Переходим на user_profile_screen")
            navController.navigate("user_screen") {
                popUpTo("start_app_screen") { inclusive = true }
            }
        } ?: Log.d("AUTO_VIEWMODEL_CHECK", "loginResult null, ничего не делаем")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Start App",
            fontSize = 24.sp,
            color = Color.White
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Логин") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                autoviewModel.login(context,login, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Войти")
        }
        Button (
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}


@Composable
fun Registration_screen(userviewModel: UserViewModel, viewModel: RegistrationViewModel, navController: NavController  ) {
    Log.d("TEST_REGISTR", "registraion_screen() started")
    // Подписка на состояние ViewModel
    val registrationResult by viewModel.registrationResult

    val errorMessage by viewModel.errorMessage
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    /*LaunchedEffect(registrationResult.value) {
        if (registrationResult == true) {


            newUser?.let { user ->
                // Записываем пользователя в глобальный UserProfileViewModel
                userviewModel.setUser(user)
            navController.navigate("exercises_screen") {
                popUpTo("registration_screen") { inclusive = true }
            }
        }
    }*/
    LaunchedEffect(registrationResult) {
        registrationResult?.let { user ->
            userviewModel.setUser(user)
            navController.navigate("user_screen") {
                popUpTo("registration_screen") { inclusive = true }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Registration",
            fontSize = 24.sp,
            color = Color.White
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Логин") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.registerUser(email, login, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зарегистрироваться")
        }

        Button (
            onClick = {
                navController.popBackStack()
                      },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }

        Spacer(Modifier.height(12.dp))

        // Показываем ошибку, если регистрация не удалась
        if (registrationResult == null) {
            Text(
                errorMessage,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
