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
import com.example.german_server.R
import java.io.File
import android.util.Log



@Composable
fun UserStatsBlock(u: BaseUser) {
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
            val avatarPainter = if (!u.avatarPath.isNullOrBlank()) {
                val file = File(u.avatarPath)
                if (file.exists()) {
                    Log.d("USER_STATS", "Avatar file exists: ${file.absolutePath}")
                    rememberAsyncImagePainter(file)
                } else {
                    Log.e("USER_STATS", "Avatar file NOT FOUND: ${file.absolutePath}")
                    rememberAsyncImagePainter(R.drawable.placeholder_avatar)
                }
            } else {
                Log.d("USER_STATS", "Avatar path is null, using placeholder")
                rememberAsyncImagePainter(R.drawable.placeholder_avatar)
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
            .padding(horizontal = 16.dp), // отступ слева/справа
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            Text("❤️ ${u.lifes}", color = Color.Red)
            Text("Баллы: ${u.score}", color = Color.Green)
            Text("⚡ ${u.shockmodLong}", color = Color.Red)

        }
    }
}
