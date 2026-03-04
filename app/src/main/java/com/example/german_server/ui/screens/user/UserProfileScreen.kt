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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.Image
import coil.compose.rememberAsyncImagePainter

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.AlertDialog

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.R
import com.example.german_server.data.ui.components.AvatarRepository
import java.io.File


@Composable
fun User_profile_screen(
    userviewModel: UserViewModel,
    navController: NavController,
) {

    val user = userviewModel.currentUser.value
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var showAvatarSourceDialog by remember { mutableStateOf(false) }
    var isPrivateExpanded by remember { mutableStateOf(false) }
    val currentUserAvatarPath by userviewModel.activeAvatarPath


    Log.d("AUTO_USER_PROFILE_SCREEN", "${user}")
    if (user == null) {
        Log.d("AUTO_USERSCREEN_NULL", "${user}")
        LaunchedEffect(Unit) { navController.navigate("start_app_screen") { popUpTo(0) } }
        return
    }
    LaunchedEffect(Unit) {
        Log.d("UserProfileScreen", "🔄 Вызов UserViewModel.loadActivveAvstar()")
        userviewModel.loadActiveAvatar()
        userviewModel.loadServerAvatar()
    }
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Log.d("AVATAR_PICKER", "Выбрана картинка: $it")
            val localPath = userviewModel.saveAvatarToInternalStorage(context, it,
                "gallery")

            localPath?.let { path ->
                userviewModel.saveGalleryAvatar(path)
               // userviewModel.addGalleryAvatar(path)
                userviewModel.updateAvatar(path)
                //userviewModel.uploadGalleryAvatar(path)
            }
        }
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
                Log.d("AVATAR_DEBUG", "LJ: ${currentUserAvatarPath} " +
                        "${user.avatarName}  --${AvatarRepository.drawableAvatars.contains(user.avatarName)}" +
                        "${user.avatarPath != null}")

                Image(
                    painter = when {
                        currentUserAvatarPath != null -> {
                            Log.d("AVATAR_DEBUG", "Используем activeAvatarPath: $currentUserAvatarPath")
                            rememberAsyncImagePainter(File(currentUserAvatarPath))
                        }
                        user.avatarName != null && AvatarRepository.drawableAvatars.contains(user.avatarName) -> {
                            Log.d("AVATAR_DEBUG", "Используем avatarName: ${user.avatarName}")
                            val resId = context.resources.getIdentifier(
                                user.avatarName,
                                "drawable",
                                context.packageName
                            )
                            painterResource(id = resId)
                        }

                        else -> {
                            Log.d("AVATAR_DEBUG", "Используем placeholder")
                            painterResource(R.drawable.placeholder_avatar)
                        }
                    },

                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(128.dp)
                        .clickable {
                            if ((user.score ?: 0) >= 5000) {
                                // >5000 очков — открыть галерею
                                //pickImageLauncher.launch("image/*")
                                showAvatarSourceDialog = true
                            } else {
                                // <5000 очков — открыть выбор из drawable

                                navController.navigate("avatar_choice_screen")
                            }

                        }

                )
                if (showAvatarSourceDialog) {
                    AlertDialog(
                        onDismissRequest = { showAvatarSourceDialog = false },
                        title = {
                            Text(text="Выберите источник аватара",
                                color=Color.Black)
                                },
                        text = {
                            Text(text="Откуда вы хотите выбрать аватар?",
                                color=Color.Black)
                               },
                        confirmButton = {
                            Button(onClick = {

                                navController.navigate("avatar_choice_screen")
                                showAvatarSourceDialog = false
                            }) {
                                Text("Аватары")
                            }
                        },
                        dismissButton = {
                            Button(onClick = {
                                pickImageLauncher.launch("image/*")
                                showAvatarSourceDialog = false
                            }) {
                                Text("Галерея")
                            }
                        }
                    )
                }

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
                        value = "${user.lifes ?: 0}",
                        valueColor = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF2A2A2A),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
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
                    icon = Icons.Default.Phone,
                    label = "Телефон",
                    value = user.phone ?: "—"
                )
                InfoRow(
                    icon = Icons.Default.DateRange,
                    label = "Дата регистрации",
                    value = userviewModel.formatDate(user.registration_date)
                )
                InfoRow(
                    icon = Icons.Default.DateRange,
                    label = "Последний вход",
                    value = userviewModel.formatDate(user.last_login_date)
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
                InfoRow(
                    icon = Icons.Default.Send,
                    label = "Telegram",
                    value = user.telegram_username ?: "—"
                )
                InfoRow(
                    label = "Chat ID",
                    value = user.chat_id?.toString() ?: "—"
                )
                InfoRow(
                    label = "Bot pass",
                    value = user.user_bot_pass ?: "—"
                )
                InfoRow(
                    icon = Icons.Default.Star,
                    label = "Администратор",
                    value = if (user.is_admin) "Да" else "Нет"
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("user_profile_edit_screen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Редактировать профиль")
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