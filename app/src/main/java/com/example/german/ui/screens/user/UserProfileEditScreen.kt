package com.example.german.ui.screens.user

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState          // ⬅️ ДОБАВЛЕНО: для скролла
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Surface
import android.widget.Toast

import androidx.compose.material3.*
import androidx.compose.ui.graphics.Brush

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect

import com.example.german.data.ui.viewModel.user_profile.UserViewModel


@Composable
fun User_profile_edit_screen(
    userviewModel: UserViewModel,
    navController: NavController,
) {

    val user = userviewModel.currentUser.value
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var isPrivateExpanded by remember { mutableStateOf(false) }

    Log.d("AUTO_USERSCREEN", "${user}")
    if (user == null) {
        Log.d("AUTO_USERSCREEN_NULL", "${user}")
        LaunchedEffect(Unit) { navController.navigate("start_app_screen") { popUpTo(0) } }
        return
    }

    // Локальные состояния для редактируемых полей
    var username by remember { mutableStateOf(user.username ?: "") }
    var email by remember { mutableStateOf(user.email) }
    var phone by remember { mutableStateOf(user.phone ?: "") }
    var telegram by remember { mutableStateOf(user.telegram_username ?: "") }
    var chatId by remember { mutableStateOf(user.chat_id?.toString() ?: "") }
    var botPass by remember { mutableStateOf(user.user_bot_pass ?: "") }

    var emailError by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6A5ACD),
                        Color(0xFF3A2F7A)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Никнейм", color = Color.White) },
                    singleLine = true,
                    isError = usernameError,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))


            }
        }
        
        Spacer(Modifier.height(24.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF2A2A2A),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Общая информация", fontSize = 20.sp, color = Color.White)
                Spacer(Modifier.height(12.dp))

                // Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color.Gray) },
                    singleLine = true,
                    isError = emailError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (emailError) {
                    Text(
                        text = "Введите корректный email",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Телефон
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Телефон", color = Color.Gray) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }

        Text(
            text = if (isPrivateExpanded)
                "▼ Личная информация"
            else
                "▶ Личная информация",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                    isPrivateExpanded = !isPrivateExpanded
                }
        )
        if (isPrivateExpanded) {

            Spacer(Modifier.height(8.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = telegram,
                    onValueChange = { telegram = it },
                    label = { Text("Telegram", color = Color.Gray) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Chat ID (readonly)
                OutlinedTextField(
                    value = chatId,
                    onValueChange = {},
                    label = { Text("Chat ID", color = Color.Gray) },
                    singleLine = true,
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Bot pass
                OutlinedTextField(
                    value = botPass,
                    onValueChange = { botPass = it },
                    label = { Text("Bot pass", color = Color.Gray) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                // Сбрасываем ошибки
                emailError = false
                usernameError = false

                // Проверка Email
                if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailError = true
                }

                // Проверка Никнейма
                if (username.isBlank()) {
                    usernameError = true
                }
                // Если всё ок, сохраняем
                if (!emailError && !usernameError) {
                    userviewModel.updateUser(email, username, phone,
                        telegram, botPass) //
                    Toast.makeText(context, "Профиль сохранён", Toast.LENGTH_SHORT).show()

                    navController.navigate("user_profile_screen") {
                        // Очистка экрана редактирования из backstack
                        popUpTo("user_profile_edit_screen") { inclusive = true }
                    }
                }


            },
            modifier = Modifier.fillMaxWidth(),

        ) {
            Text("Сохранить изменения")
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = { navController.navigate("user_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
        Spacer(Modifier.height(24.dp))
    }
}