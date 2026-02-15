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

import androidx.compose.material.icons.filled.*
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.Image

import androidx.compose.material3.Text
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext

import androidx.navigation.NavController

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.components.AvatarRepository

@Composable
fun AvatarChoiceScreen(
    userviewModel: UserViewModel,
    navController: NavController,
    context: Context = LocalContext.current
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Выберите аватар",
            //fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(AvatarRepository.drawableAvatars) { avatarName ->
                Log.d("DRAW_AVATAR_NMAE_UPDATE", "Avatar updated to $avatarName")
                val resId = context.resources.getIdentifier(
                    avatarName,
                    "drawable",
                    context.packageName
                )
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = avatarName,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                        .clickable {
                            // Выбрали аватар
                            userviewModel.updateAvatarName(avatarName)
                            //userviewModel.updateProfileOnServer()
                            navController.popBackStack()
                        }
                )
            }
        }
    }
}
