package com.example.german_server.ui.screens.user

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
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

import com.example.german_server.data.ui.components.InfoRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Brush

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import coil.compose.rememberAsyncImagePainter

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.R
import com.example.german_server.data.ui.components.AvatarRepository
@Composable
fun Another_profile_screen(
    userviewModel: UserViewModel,
    navController: NavController,
) {

    val user = userviewModel.selectedUser.value
    val scrollState = rememberScrollState()
    var isPrivateExpanded by remember { mutableStateOf(false) }
    val BASE_URL = "https://alexdirect.pythonanywhere.com/"

    Log.d("ANOTHER_SCREEN", "${user}")
    if (user == null) {
        Log.d("ANOTHER_USERSCREEN_NULL", "${user}")
        LaunchedEffect(Unit) { navController.navigate("start_app_screen") { popUpTo(0) } }
        return
    }

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
                val serverAvatar = user.avatarFullUrl
                val avatarPainter = when {
                    !serverAvatar.isNullOrBlank() -> {
                        val cleanBase = BASE_URL.removeSuffix("/")
                        val cleanPath = serverAvatar.removePrefix("/")
                        val fullUrl = "$cleanBase/$cleanPath"

                        Log.d("RatingScreen", "🌍 FULL URL = $fullUrl")

                        rememberAsyncImagePainter(model = fullUrl)
                    }

                    !user.avatarName.isNullOrBlank() &&
                            AvatarRepository.drawableAvatars.contains(user.avatarName) -> {

                        val resId = LocalContext.current.resources.getIdentifier(
                            user.avatarName,
                            "drawable",
                            LocalContext.current.packageName
                        )

                        painterResource(id = resId)
                    }

                    else -> {
                        painterResource(id = R.drawable.placeholder_avatar)
                    }
                }
                Image(
                    painter = avatarPainter,
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                InfoRow(
                    icon = Icons.Default.Star,   // если хочешь настоящую иконку, а не символ
                    label = "Никнейм",
                    value = "${user.username ?: ""}",
                    valueColor = Color.White,
                    valueFontSize = 22.sp,
                )
                Spacer(Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp)
                ) {
                    InfoRow(
                        icon = Icons.Default.Star,   // если хочешь настоящую иконку, а не символ
                        label = "",
                        value = "${user.score ?: 0}",
                        valueColor = Color.White,
                    )
                    InfoRow(
                        icon = Icons.Default.Face,
                        label = "",
                        value = "${user.streakDays ?: 0}",
                        valueColor = Color.White
                    )
                }
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

                // ⚡ Заменили на InfoRow
                InfoRow(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = user.email
                )
                InfoRow(
                    icon = Icons.Default.DateRange,
                    label = "Дата регистрации",

                    value = userviewModel.formatDate(
                        userviewModel.parseIsoToLong(user.createdAt)
                    )
                )
                InfoRow(
                    icon = Icons.Default.DateRange,
                    label = "Последний вход",
                    value = user.lastSessionDate?.let { userviewModel.formatDate(it) } ?: "-"
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
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate("rating_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
        Spacer(Modifier.height(24.dp))
    }
}