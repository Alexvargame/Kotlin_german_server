package com.example.german.ui.screens

import android.app.Activity
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

import com.example.german.data.ui.autorization.AutorizationViewModel
import com.example.german.data.ui.registration.RegistrationViewModel
import com.example.german.data.AppDatabase
import com.example.german.data.repository.autorization.AutorizationViewModelFactory

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField

import androidx.navigation.NavController


@Composable
fun Start_app_screen(viewModel: AutorizationViewModel, navController: NavController) {

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    val errorMessage by viewModel.errorMessage
    val loginResult by viewModel.loginResult


    LaunchedEffect(loginResult) {
        if (loginResult == true) {
            navController.navigate("exercises_screen") {
                popUpTo("start_app_screen") { inclusive = true }
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
                viewModel.login(login, password)
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
fun Registration_screen(viewModel: RegistrationViewModel, navController: NavController  ) {
    Log.d("TEST_REGISTR", "registraion_screen() started")
    // Подписка на состояние ViewModel
    val registrationResult by viewModel.registrationResult

    val errorMessage by viewModel.errorMessage
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(registrationResult) {
        if (registrationResult == true) {
            navController.navigate("exercises_screen") {
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
        if (registrationResult == false) {
            Text(
                errorMessage,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
