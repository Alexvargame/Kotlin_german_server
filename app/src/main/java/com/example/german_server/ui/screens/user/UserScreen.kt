package com.example.german_server.ui.screens.user

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color


import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.german_server.data.ui.components.UserStatsBlock

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.viewModel.support_chat.SupportChatViewModel
import com.example.german_server.data.ui.viewModel.daily_quests.DailyQuestViewModel
import kotlinx.coroutines.flow.map

@Composable
fun User_screen(
    userviewModel: UserViewModel,
    supportChatViewModel: SupportChatViewModel,
    navController: NavController,
    dailyQuestViewModel: DailyQuestViewModel
) {

    val user = userviewModel.currentUser.value

    val unreadCount = supportChatViewModel.messages
        .map { messages -> messages.count {

            Log.d("MESSAGE_UNREAD_USER_SCREEN", "${it.receiverUid} /  ${user?.serverUid}")
            Log.d("MESSAGE_UNREAD_USER_SCREEN", "${it.receiverUid ==  user?.serverUid}")
            !it.is_read && it.receiverUid == user?.serverUid
        } }
        .collectAsState(initial = 0)

    Log.d("AUTO_USERSCREEN", "${user}")
    Log.d("AUTO_USERSCREEN_MODEL", "${userviewModel.currentUser} , ${userviewModel}")

    // ⬇️⬇️⬇️ ПРОВЕРКА ВЕРИФИКАЦИИ ПРИ ЗАХОДЕ ⬇️⬇️⬇️
    LaunchedEffect(user) {
        Log.d("USER_SCREEN_DEBUG", "LaunchedEffect, user = $user")


    }
    // Проверка флага и генерация заданий
    LaunchedEffect(user?.id) {
        user?.let {
            Log.d("TEST_DB_USER_Flag1", "${user.lastQuestReset}")
//            userviewModel.resetLastQuestFlag()
            Log.d("TEST_DB_USER_Flag2", "${user.lastQuestReset}")
//            val hasQuests = dailyQuestViewModel.checkHasQuests(it.id)
//            Log.d("TEST_DB_USER_Flag_has", "${hasQuests}")
//            if (!hasQuests) {
//                userviewModel.resetLastQuestFlag()
//                Log.d("TEST_DB_USER_Flag", "Нет заданий, флаг сброшен")
//            }
//            Log.d("TEST_DB_USER_Flag", "${user.lastQuestReset}/ ${it.lastQuestReset}")
            if (!it.lastQuestReset) {
                dailyQuestViewModel.generateAndLoadQuests(it.id)
                Log.d("TEST_DB_USER_Flag3", "${user.lastQuestReset}")
                userviewModel.resetLastQuestFlag()
                Log.d("TEST_DB_USER_Flag4", "${user.lastQuestReset}/ ${it.lastQuestReset}")
            }
        }
    }

    LaunchedEffect(Unit) {
        Log.d("UserScreen", "🔄 Вызов UserViewModel.loadActiceAvatar()")
        userviewModel.loadActiveAvatar()
        userviewModel.loadServerAvatar()
    }
    // ⬆️⬆️⬆️ КОНЕЦ ПРОВЕРКИ ⬆️⬆️⬆️
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
    ) { // ВЕРИФИКАЦИЯ
        user.let { u ->
            if (!u.emailVerified) {
                val daysLeft = userviewModel.getDaysLeft(u)

                if (daysLeft == 0) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("🚫 Доступ заблокирован", color = Color.Red)
                        Button(
                            onClick = { /* TODO */ },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Отправить письмо верификации")
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                } else if (daysLeft > 0) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text("⚠️ Требуется верификация")
                        Text("Осталось дней: $daysLeft")
                        Button(
                            onClick = {
                                user?.email?.let { email ->
                                    userviewModel.resendVerification(email)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Отправить письмо верификации")
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
        user?.let { u ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                UserStatsBlock(u, userviewModel)
            }
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
            onClick = { navController.navigate("rating_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Рейтинг")
        }
        Button(
            onClick = { navController.navigate("support_chat_message_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сообщения (${unreadCount.value})")
        }

        // Пункт меню для просмотра заданий
        Button(
            onClick = {
                // Открыть экран с заданиями
                navController.navigate("daily_quests_screen")//""daily_quests/${user?.id}")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ежедневные задания")
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