package com.example.german_server.ui.screens

import android.app.Activity


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.german_server.data.ui.components.GreetingButton

import com.example.german_server.data.ui.viewModel.autorization.AutorizationViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.repository.autorization.AutorizationViewModelFactory
import com.example.german_server.data.repository.user_profile.UserViewModelFactory
import com.example.german_server.data.AppDatabase

@Composable
fun HomeScreen(navController: NavController, greetingText: String) {
    // state переменная
    val context = LocalContext.current

    var textHello by remember { mutableStateOf("$greetingText,\nхотите знать немецкий?") }
    val autoviewModel: AutorizationViewModel = viewModel(
        factory = AutorizationViewModelFactory(
            AppDatabase.getInstance(context)
        )
    )
    val userDao = AppDatabase.getInstance(context).baseUserDao()
    val userviewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(userDao)
    )


    androidx.compose.material3.MaterialTheme {  // ← ДОБАВЛЕНО
        Column(
            modifier = Modifier                   // ← ИСПРАВЛЕНО: заменено на импортированный Modifier
                .fillMaxSize()                    // ← ДОБАВЛЕНО: чтобы занять весь экран
                .padding(36.dp),                  // ← ДОБАВЛЕНО: отступы
        )
        {

            Spacer(modifier = Modifier.height(40.dp))
            Text(textHello,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 24.sp,      // размер шрифта
                maxLines = 2,          // максимум 2 строки
                overflow = TextOverflow.Ellipsis // если вдруг текст длинный
            )
            Column(
                modifier = Modifier                   // ← ИСПРАВЛЕНО: заменено на импортированный Modifier
                    .fillMaxSize()                    // ← ДОБАВЛЕНО: чтобы занять весь экран
                    .padding(36.dp),                  // ← ДОБАВЛЕНО: отступы
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                GreetingButton( text = "Войти",
                    onClick = { navController.navigate("start_app") })

                Spacer(modifier = Modifier.height(16.dp))
                if (!autoviewModel.checkUserLoggedIn(context)) {
                    GreetingButton(
                        text = "Зарегистрироваться",
                        onClick = { navController.navigate("registration")}
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                GreetingButton(text = "Back Up",
                    onClick = {
                        navController.navigate("back_up_screen")
                    })
                Spacer(modifier = Modifier.height(16.dp))
                if (autoviewModel.checkUserLoggedIn(context)) {
                    GreetingButton(
                        text = "Выйти",
                        onClick = {
                            (context as? Activity)?.finish()
                        }
                    )
                }
                //GreetingButton(text = "Выйти",
                  //  onClick = {(context as? Activity)?.finish()})
                Spacer(modifier = Modifier.height(16.dp))
                GreetingButton(
                    text = if (autoviewModel.checkUserLoggedIn(context)) "Разлогиниться" else "Выйти",
                    onClick = {
                        if (autoviewModel.checkUserLoggedIn(context)) {
                            autoviewModel.logout(context)
                            userviewModel.logout()
                            navController.navigate("home")
                        }
                        else
                        (context as? Activity)?.finish()
                    }
                )
                //GreetingButton(text = "Очень жаль",
                  //  onClick = {(context as? Activity)?.finish()})
            }
        }
    }
}


