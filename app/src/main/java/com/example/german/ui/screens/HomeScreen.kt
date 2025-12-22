package com.example.german.ui.screens

import android.app.Activity
import android.util.Log
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
import com.example.german.GreetingButton


@Composable
fun HomeScreen(navController: NavController, greetingText: String) {
    // state переменная
    val context = LocalContext.current
    var textHello by remember { mutableStateOf("$greetingText,\nхотите знать немецкий?") }

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
                GreetingButton( text = "Зарегистрироваться",
                    onClick = {
                        Log.d("REG_SCREEN", "Нажата кнопка Регистрация")
                        navController.navigate("registration")
                    })
                Spacer(modifier = Modifier.height(16.dp))
                GreetingButton(text = "Очень жаль",
                    onClick = {(context as? Activity)?.finish()})
            }
        }
    }
}


