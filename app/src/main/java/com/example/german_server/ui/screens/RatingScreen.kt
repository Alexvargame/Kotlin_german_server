package com.example.german_server.ui.screens

import android.util.Log
import androidx.annotation.InspectableProperty
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color


import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import android.net.Uri





import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel

import androidx.compose.ui.text.font.FontWeight
import com.example.german_server.data.network.models.LeaderboardUser
import com.example.german_server.data.network.models.SortType
import com.example.german_server.data.ui.components.AvatarRepository
import com.example.german_server.R



@Composable
fun Rating_screen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    // --- Лог: экран создан
    Log.d("RatingScreen", "🏁 RatingScreen создан")

   // var activeAvatarPath by remember { mutableStateOf<String?>(null) }


    //var byScore by remember { mutableStateOf(true) }
    var sortType by remember { mutableStateOf(SortType.SCORE) }
    // =========================
    // Вызов функции модели для загрузки рейтинга
    // =========================
    LaunchedEffect(Unit) {
        Log.d("RatingScreen", "🔄 Вызов UserViewModel.loadLeaderboard()")
        userViewModel.loadLeaderboard()
        userViewModel.loadActiveAvatar()
    }

    // =========================
    // Получаем состояние рейтинга из модели
    // =========================
    val leaderboardState = userViewModel.leaderboardState
    val currentUser = userViewModel.currentUser.value// id текущего пользователя
    val isLoading = leaderboardState == null

    val currentUserAvatarPath by userViewModel.activeAvatarPath



 //   Log.d("RatingScreen", "⏳ ${userViewModel.} ")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- Кнопка назад
        Button(
            onClick = {
                Log.d("RatingScreen", "🔙 Нажата кнопка назад")
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }

        Spacer(modifier = Modifier.height(16.dp))
        // --- Переключение сортировки
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { sortType = SortType.SCORE },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (sortType == SortType.SCORE) Color(0xFF1E88E5) else Color.DarkGray
                )
            ) { Text("По очкам", color = Color.White) }

            Button(
                onClick = { sortType = SortType.DAYS },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (sortType == SortType.DAYS) Color(0xFF1E88E5) else Color.DarkGray
                )
            ) { Text("По дням", color = Color.White) }
        }
        // --- Индикатор загрузки
        if (isLoading) {
            Log.d("RatingScreen", "⏳ Показываем прогресс")
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        // --- Данные рейтинга

        else {
            leaderboardState?.let { state ->
                Log.d("RatingScreen", "📊 Отображаем рейтинг на экране")
                val users = if (sortType == SortType.SCORE) state.scoreTop else state.streakTop
                val myRank = if (sortType == SortType.SCORE) state.scoreMyRank else state.streakMyRank
                val valueType = if (sortType == SortType.SCORE) "score" else "streak"
                val isCurrentUserInTable = users.any { it.uid == currentUser?.serverUid }
                Column {
                    Text("🏆 Топ по очкам:", fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.White)
                    LeaderboardTable(
                        users = users,
                        currentUserUid = currentUser?.serverUid,
                        currentUserAvatarPath = currentUserAvatarPath,
                        valueType =  valueType,
                        formatDate = { ts -> ts?.let { userViewModel.formatDate(it) } ?: "-" },
                        onUserClick = { user ->
                            if (user.uid == currentUser?.serverUid) {
                                navController.navigate("user_profile_screen")
                            } else {
                                userViewModel.selectUser(user)
                                navController.navigate("another_profile_screen")
                            }
                        }
                    )

                    if (!isCurrentUserInTable) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Мой ранг: ${myRank ?: "-"}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }

                }
            }
        }
    }
}

// =========================
// Composable таблицы рейтинга
// =========================
@Composable
fun LeaderboardTable(
    users: List<LeaderboardUser>,   // список пользователей
    currentUserUid: String?, // id текущего пользователя для выделения
    currentUserAvatarPath: String?,
    valueType: String,
    formatDate: (Long) -> String,
    onUserClick: (LeaderboardUser) -> Unit
) {

    Column(
    modifier = Modifier
        .fillMaxWidth()
        .background(Color.Black)  // фон чёрный
        .padding(8.dp)
    )
    {
        // Заголовок таблицы
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)) {
            Text("", modifier = Modifier.weight(0.5f),
                fontWeight = FontWeight.Bold,  color = Color.White)
            Text("№", modifier = Modifier.weight(0.5f),
                fontWeight = FontWeight.Bold,  color = Color.White)
            Text("Ник", modifier = Modifier.weight(2f),
                fontWeight = FontWeight.Bold, color = Color.White)
            Text(if (valueType == "score") "Очки" else "Серия",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                color = Color.White)
            Text("логин",
                modifier = Modifier.weight(1.5f),
                fontWeight = FontWeight.Bold,
                color = Color.White)
        }

        Divider(color = Color.Gray, thickness = 1.dp)

        // Список пользователей
        LazyColumn {
            itemsIndexed(users) { index, user ->
                val backgroundColor =
                    if (user.uid == currentUserUid) Color.LightGray else Color.Black
                val requestUserColor =
                    if (user.uid == currentUserUid) Color.Blue else Color.Green

                val isCurrent = user.uid == currentUserUid

// Получаем painter для аватара
                val avatarPainter = if (isCurrent) {
                    // Текущий игрок
                    Log.d("RatingScreen", "📊 curernt AVABTAR path ${currentUserAvatarPath}")
                    when {
                        !currentUserAvatarPath.isNullOrBlank() ->
                            rememberAsyncImagePainter(Uri.parse(currentUserAvatarPath))
                        !user?.avatarName.isNullOrBlank() &&
                                AvatarRepository.drawableAvatars.contains(user?.avatarName) -> {
                            painterResource(
                                id = LocalContext.current.resources.getIdentifier(user!!.avatarName,
                                    "drawable", LocalContext.current.packageName
                                )
                            )
                        }
                        else -> painterResource(id = R.drawable.placeholder_avatar)
                    }
                } else {
                    // Другие пользователи
                    if (!user.avatarName.isNullOrBlank() &&
                        AvatarRepository.drawableAvatars.contains(user.avatarName)) {
                        Log.d("RatingScreen", "PATH ${user.avatarName}")
                        painterResource(
                            id = LocalContext.current.resources.getIdentifier(
                                user.avatarName, "drawable", LocalContext.current.packageName
                            )
                        )
                    } else {
                        painterResource(id = R.drawable.placeholder_avatar)
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        //.background(backgroundColor)
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                ) {
                    Image(
                        painter = avatarPainter,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                    )
                    Text("${index + 1}", modifier = Modifier.weight(0.5f),
                        color = requestUserColor)
                    Text(user.username ?: "-",
                        modifier = Modifier
                            .weight(2f)
                            .clickable {
                                onUserClick(user)

                            },
                        color = requestUserColor)
                    Text(
                        text = if (valueType == "score") user.score.toString()
                        else user.streakDays.toString(),
                        modifier = Modifier.weight(1f),  color = requestUserColor
                    )
                    Text(text = user.lastSessionDate?.let { formatDate(it) } ?: "-",
                        modifier = Modifier.weight(1.5f),  color = requestUserColor)
                }

                Divider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }
    }
}
