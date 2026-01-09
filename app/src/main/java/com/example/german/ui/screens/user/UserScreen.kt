package com.example.german.ui.screens.user

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color


import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect

import com.example.german.data.ui.viewModel.user_profile.UserViewModel

@Composable
fun User_screen(
    userviewModel: UserViewModel,
    navController: NavController,
) {

    val user = userviewModel.currentUser.value
    val context = LocalContext.current
    Log.d("AUTO_USERSCREEN", "${user}")
    Log.d("AUTO_USERSCREEN_MODEL", "${userviewModel.currentUser} , ${userviewModel}")
    if (user == null) {
        Log.d("AUTO_USERSCREEN_NULL", "${user}")
        // Если кто-то попал без логина — вернём на старт
        LaunchedEffect(Unit) { navController.navigate("start_app_screen") { popUpTo(0) } }
        return
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        user?.let {
            Text(
                "Добро пожаловать, ${it.username}\n" +
                        "Ваши баллы: ${it.score}. Ваши жизни: ${it.lifes}",
                fontSize = 24.sp,
                color = Color.White
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {Log.d("USER_SCREEN_MODEL", "profile")
                navController.navigate("user_profile_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ваш профиль")
        }
        Button(
            onClick = { navController.navigate("exercises_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Упражнения")
        }
        Button(
            onClick = { /* TODO вход */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ЧТО ТО")
        }

        Button (
            onClick = {
                navController.navigate("home")
                       },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("На главную")
        }

    }
}