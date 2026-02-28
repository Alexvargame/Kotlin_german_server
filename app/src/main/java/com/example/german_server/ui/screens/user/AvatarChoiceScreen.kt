package com.example.german_server.ui.screens.user

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.Image

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue


import android.content.Context
import androidx.compose.foundation.lazy.grid.items

import androidx.navigation.NavController
import coil.compose.rememberImagePainter

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.components.AvatarRepository
import java.io.File
import androidx.compose.runtime.LaunchedEffect
@Composable
fun AvatarChoiceScreen(
    userviewModel: UserViewModel,
    navController: NavController,
    context: Context = LocalContext.current
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔹 Верхний Row с кнопкой Назад
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "← Назад",
                    fontSize = 18.sp,
                   // fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable { navController.popBackStack() }
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Выберите аватар",
                    fontSize = 20.sp,
                    color = Color.White,
                    //fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val galleryAvatars = userviewModel.loadAllAvatars().value
            val allAvatars by remember {
                derivedStateOf {
                    AvatarRepository.drawableAvatars + galleryAvatars
                }
            }

            Log.d("AVATAR_LIST", "${galleryAvatars}")
            Log.d("AVATAR_LIST_1", "${allAvatars}")
            // 🔹 Сетка аватаров
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(allAvatars) { avatarName ->
                    val isGallery = avatarName.endsWith(".png")

                    val painter = if (isGallery) {
                        Log.d("AVATAR_UI", "Галерейный аватар: $avatarName")
                        rememberImagePainter(File(avatarName))
                    } else {
                        val resId = context.resources.getIdentifier(
                            avatarName, "drawable", context.packageName
                        )
                        Log.d("AVATAR_UI_1", "Стандартный аватар: $avatarName, resId=$resId")
                        painterResource(resId)
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painter,
                            contentDescription = avatarName,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.Gray, CircleShape)
                                .clickable {

                                    Log.d("AVATAR_UI", "Выбран аватар: $avatarName")
                                    val finalAvatarName = if (isGallery) {
                                        Log.d("AVATAR_UI", "IF : ")
                                        File(avatarName).name
                                    } else {
                                        Log.d("AVATAR_UI", "ELSE : ")
                                        avatarName
                                    }
                                    Log.d("AVATAR_UI", "Выбран аватар_имя: $finalAvatarName")
//                                    userviewModel.updateAvatarName(finalAvatarName)
                                    if (isGallery) {
//                                        Log.d("AVATAR_UI", "Активный галерейный аватар обновлён")
                                        userviewModel.updateAvatarPath(avatarName)
                                    }
                                    else {
                                        userviewModel.updateAvatarPath(null)
//                                        Log.d("AVATAR_UI", "Выбран стандартный аватар")
                                        userviewModel.deactivateAllAvatars()
                                        userviewModel.updateAvatarName(finalAvatarName)
                                    }
                                    navController.popBackStack()
                                }
                        )

                        if (isGallery) {
                            Text(
                                text = "Удалить",
                                color = Color.Red,
                                modifier = Modifier
                                    .clickable {
                                        Log.d("AVATAR_UI", "Удаление галерейного аватара: $avatarName")
                                        userviewModel.deleteGalleryAvatar(avatarName)
                                    }
                                    .padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 🔹 Кнопка Подтвердить выбор аватара
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Подтвердить")
            }
        }

        // 🔹 Можно добавить эффект поверх, как Confetti на экране упражнения
        // ConfettiEffect(modifier = Modifier.fillMaxSize(), play = false)
    }
}