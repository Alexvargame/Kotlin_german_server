package com.example.german_server.ui.screens.daily_quests

import androidx.navigation.NavHostController



import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.german_server.data.entities.DailyQuestEntity
import com.example.german_server.data.ui.viewModel.daily_quests.DailyQuestViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel

private const val TAG = "DailyQuestScreen"

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DailyQuestScreen(
//    dailyQuestViewModel: DailyQuestViewModel,
//    userviewModel: UserViewModel,
//    navController: NavHostController
//) {
//    val user = userviewModel.currentUser.value
//    val quests by dailyQuestViewModel.quests.collectAsState()
//    val isLoading by dailyQuestViewModel.isLoading.collectAsState()
//
//    // Логи
//    Log.d(TAG, "Screen open, userId=${user?.id}")
//    Log.d(TAG, "isLoading=$isLoading, quests size=${quests.size}")
//
//    LaunchedEffect(Unit) {
//        Log.d(TAG, "Load quests for userId=${user?.id}")
//        user?.let {
//            dailyQuestViewModel.loadTodayQuests(it.id)
//        }
//    }
//
//    LaunchedEffect(quests) {
//        Log.d(TAG, "Quests updated: ${quests.size} items")
//        quests.forEach { q ->
//            Log.d(TAG, "Quest: id=${q.id}, title=${q.questTitle}, progress=${q.progress}/${q.target}, completed=${q.isCompleted}")
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text("Ежедневные задания") }
//            )
//        }
//    ) { paddingValues ->
//
//        when {
//            isLoading -> {
//                Log.d(TAG, "Show loading")
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//            quests.isEmpty() -> {
//                Log.d(TAG, "No quests")
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text("Нет заданий на сегодня")
//                }
//            }
//            else -> {
//                Log.d(TAG, "Show ${quests.size} quests")
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(paddingValues),
//                    contentPadding = PaddingValues(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(quests) { quest ->
//                        Log.d(TAG, "Render card: ${quest.questTitle}")
//                        QuestCard(quest = quest)
//                    }
//                }
//            }
//        }
//        Button (
//            onClick = {
//                navController.navigate("user_screen")
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Назад")
//        }
//
//    }
//}
//
//fun Modifier.Companion.align(bottomCenter: Alignment) {}
//
//@Composable
//fun QuestCard(quest: DailyQuestEntity) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = if (quest.isCompleted)
//                MaterialTheme.colorScheme.primaryContainer
//            else
//                MaterialTheme.colorScheme.surface
//        )
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = quest.questTitle,
//                style = MaterialTheme.typography.titleMedium
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = "Прогресс: ${quest.progress}/${quest.target}",
//                style = MaterialTheme.typography.bodyMedium
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            LinearProgressIndicator(
//                progress = quest.progress.toFloat() / quest.target.toFloat(),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "⭐ Опыт: ${quest.rewardScore}",
//                    style = MaterialTheme.typography.bodySmall
//                )
//                Text(
//                    text = "🪙 Монеты: ${quest.rewardCoins}",
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//
//            if (quest.isCompleted) {
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = "✅ Выполнено!",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//        }
//    }
//}

@Composable
fun DailyQuestScreen(
    dailyQuestViewModel: DailyQuestViewModel,
    userviewModel: UserViewModel,
    navController: NavHostController
) {
    val user = userviewModel.currentUser.value
    val quests by dailyQuestViewModel.quests.collectAsState()
    val isLoading by dailyQuestViewModel.isLoading.collectAsState()

    Log.d(TAG, "🖥️ Экран открыт, userId=${user?.id}")
    Log.d(TAG, "📊 isLoading=$isLoading, quests size=${quests.size}")

    LaunchedEffect(Unit) {
        Log.d(TAG, "🔄 LaunchedEffect: загрузка заданий для userId=${user?.id}")
        user?.let {
            dailyQuestViewModel.loadTodayQuests(it.id)
        }
    }

    LaunchedEffect(quests) {
        Log.d(TAG, "📋 Список заданий обновлён: ${quests.size} шт.")
        quests.forEach { q ->
            Log.d(TAG, "   - id=${q.id}, title=${q.questTitle}, progress=${q.progress}/${q.target}, completed=${q.isCompleted}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Log.d(TAG, "🎨 Рендерим Column с заголовком")

        Text(
            text = "Ежедневные задания",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Список заданий
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .padding(8.dp)
        ) {
            when {
                isLoading -> {
                    Log.d(TAG, "⏳ Показываем индикатор загрузки")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                quests.isEmpty() -> {
                    Log.d(TAG, "📭 Нет заданий на сегодня")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Нет заданий на сегодня")
                    }
                }
                else -> {
                    Log.d(TAG, "📜 Отображаем LazyColumn с ${quests.size} заданиями")
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(quests, key = { it.id }) { quest ->
                            Log.d(TAG, "🎴 Рендерим карточку: id=${quest.id}, title=${quest.questTitle}")
                            DailyQuestCard(
                                quest = quest,
                                currentUserId = user?.id ?: 0
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                Log.d(TAG, "⬅️ Нажата кнопка Назад, переход на user_screen")
                navController.navigate("user_screen")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}

@Composable
fun DailyQuestCard(
    quest: DailyQuestEntity,
    currentUserId: Long
) {
    Log.d(TAG, "🃏 DailyQuestCard: id=${quest.id}, title=${quest.questTitle}, progress=${quest.progress}/${quest.target}, completed=${quest.isCompleted}")

    val isCompleted = quest.isCompleted
    val progress = quest.progress
    val target = quest.target
    val progressPercent = if (target > 0) progress.toFloat() / target.toFloat() else 0f

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) Color.DarkGray else Color(0xFF2D2D2D),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = quest.questTitle,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
                if (isCompleted) {
                    Text(
                        text = "✅ ВЫПОЛНЕНО",
                        color = Color.Green,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Прогресс: $progress / $target",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            LinearProgressIndicator(
                progress = progressPercent,
                modifier = Modifier.fillMaxWidth(),
                color = if (isCompleted) Color.Green else Color.Blue,
                trackColor = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "⭐ Опыт: ${quest.rewardScore}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
                Text(
                    text = "🪙 Монеты: ${quest.rewardCoins}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
    }
}
