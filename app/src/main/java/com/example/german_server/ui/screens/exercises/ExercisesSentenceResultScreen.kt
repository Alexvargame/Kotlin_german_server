package com.example.german_server.ui.screens.exercises

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment


import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.Color

import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.example.german_server.data.ui.components.UserStatsBlock

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.components.ConfettiEffect

@Composable
fun ExerciseSentenceResultScreen(
    isCorrect: Boolean,
    userAnswer: String,
    correctAnswer: String,
    translation: String,
    description: String,
    navController: NavController,
    userProfileViewModel: UserViewModel
) {
    val user = userProfileViewModel.currentUser.value
    var playConfetti by remember { mutableStateOf(false) }

    // Если все ответы верны — включаем конфетти
    LaunchedEffect(Unit) {
        if (isCorrect) {
            playConfetti = true
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isCorrect) "✓ Верно!" else "✗ Ошибка",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = if (isCorrect) Color.Green else Color.Red
            )

            Spacer(modifier = Modifier.height(16.dp))

            user?.let { u ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    UserStatsBlock(u, userProfileViewModel)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Твой ответ
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text("Твой ответ:", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(userAnswer, fontSize = 18.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Правильно
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text("Правильно:", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(correctAnswer, fontSize = 18.sp, color = Color(0xFF1976D2))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Перевод
            if (translation.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text("Перевод:", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(translation, fontSize = 18.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Объяснение
            if (description.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFF8E1), shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text("Объяснение:", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(description, fontSize = 18.sp, color = Color.DarkGray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("exercise_sentence_screen") {
                        popUpTo("exercises_screen") { inclusive = false }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Повторить")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.popBackStack("exercises_screen", false)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Назад")
            }
        }

        ConfettiEffect(
            modifier = Modifier.fillMaxSize(),
            play = playConfetti
        )
    }
}