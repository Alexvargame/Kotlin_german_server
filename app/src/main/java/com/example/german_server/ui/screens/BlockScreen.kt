package com.example.german_server.ui.screens


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import android.util.Log
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text



import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.viewModel.autorization.AutorizationViewModel



@Composable
fun BlockScreen(
    userviewModel: UserViewModel,
    autoviewModel: AutorizationViewModel,
    navController: NavController,
) {
    val userEmail = userviewModel.currentUser.value?.email ?: ""
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🚫 Доступ заблокирован",
            fontSize = 22.sp,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Вы не подтвердили email ($userEmail) в течение 7 дней.",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка "Отправить письмо верификации"
        Button(
            onClick = {
                userviewModel.resendVerification(userEmail)
                // Можете показать Snackbar: "Письмо отправлено"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📧 Отправить письмо верификации")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Кнопка "Удалить аккаунт"
        Button(
            onClick = {
                val uid = userviewModel.currentUser.value?.serverUid
                Log.e("DELETE_ACCOUNT_SCREEN", "${uid}")
                if (uid != null) {
                    // Вызываем метод ViewModel, как в других экранах
                    userviewModel.deleteAccount(uid) { success ->
                        if (success) {
                            autoviewModel.logout(context)
                            navController.navigate("start_app_screen") { popUpTo(0) }
                        }
                    }
                }
            }
        ) {
            Text("Удалить аккаунт")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Кнопка "Выйти"
        Button(
            onClick = {
                    autoviewModel.logout(context)
                    userviewModel.logout()
                    navController.navigate("home")
                },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🚪 Выйти из аккаунта")
        }
    }
}