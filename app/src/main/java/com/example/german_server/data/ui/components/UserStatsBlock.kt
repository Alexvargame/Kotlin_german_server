package com.example.german_server.data.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.size

import android.net.Uri

import androidx.compose.foundation.shape.RoundedCornerShape
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.german_server.data.entities.BaseUser
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.R
import java.io.File
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource


@Composable
fun UserStatsBlock(u: BaseUser,
                   userviewModel: UserViewModel) {

    val currentUserAvatarPath by userviewModel.activeAvatarPath
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2A2A2A), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Log.d("AVATAR_BLOCK", "==============================")
            Log.d("AVATAR_BLOCK", "currentUserAvatarPath: $currentUserAvatarPath")
            Log.d("AVATAR_BLOCK", "u.avatarPath: ${u.avatarPath}")
            Log.d("AVATAR_BLOCK", "u.avatarName: ${u.avatarName}")

            val avatarPainter = when {
                !currentUserAvatarPath.isNullOrBlank() -> {

                    val file = File(currentUserAvatarPath!!)
                    Log.d("AVATAR_BLOCK", "Using ACTIVE avatar path")
                    Log.d("AVATAR_BLOCK", "File exists: ${file.exists()}")
                    Log.d("AVATAR_BLOCK", "Full path: $currentUserAvatarPath")

                    if (file.exists()) {
                        rememberAsyncImagePainter(currentUserAvatarPath)
                    } else {
                        Log.d("AVATAR_BLOCK", "Active avatar file NOT FOUND → placeholder")
                        rememberAsyncImagePainter(R.drawable.placeholder_avatar)
                    }
                }

                !u.avatarName.isNullOrBlank() -> {
                    Log.d("AVATAR_BLOCK", "Using avatarName drawable: ${u.avatarName}")
                    val resId = context.resources.getIdentifier(
                        u.avatarName,
                        "drawable",
                        context.packageName
                    )

                    if (resId != 0) {
                        painterResource(resId)
                    } else {
                        Log.d("AVATAR_BLOCK", "Drawable avatar found → placeholder")
                        rememberAsyncImagePainter(R.drawable.placeholder_avatar)
                    }
                }

                else -> {
                    Log.d("AVATAR_BLOCK", "No avatar found → placeholder")
                    rememberAsyncImagePainter(R.drawable.placeholder_avatar)
                }
            }
            Image(
                painter = avatarPainter,
                contentDescription = "User avatar",
                modifier = Modifier
                    .size(56.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                " ${u.username}",
                color = Color.Blue,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row (
            modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Text("❤️ ${u.lifes}", color = Color.Red)
            Text("Баллы: ${u.score}", color = Color.Green)
            Text("⚡ ${u.shockmodLong}", color = Color.Red)

        }
    }
}
