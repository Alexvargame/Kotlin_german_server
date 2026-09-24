package com.example.german_server.ui.screens.exercises.pronouns

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.example.german_server.data.entities.exercises.ArticleAnswerDetail
import com.example.german_server.data.entities.exercises.PronounAnswerDetail
import com.example.german_server.data.ui.components.ConfettiEffect
import com.example.german_server.data.ui.components.UserStatsBlock

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel

@Composable
fun ExercisePronounEnterResultScreen(
    correctCount: Int,
    totalQuestions: Int,
    details: List<PronounAnswerDetail>,
    navController: NavController,
    userProfileViewModel: UserViewModel
) {
    val user = userProfileViewModel.currentUser.value
    var playConfetti by remember { mutableStateOf(false) }

    // Если все ответы верны — включаем конфетти
    LaunchedEffect(Unit) {
        if (correctCount == totalQuestions) {
            playConfetti = true
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Вы ответили на $correctCount из $totalQuestions вопросов",
            color=Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        user?.let { u ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                UserStatsBlock(u,userProfileViewModel)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            items(details) { detail ->
                val isCorrect = detail.userAnswer == detail.correctAnswer
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${detail.word} (${detail.casus})",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Text(
                        text = if (isCorrect) "✓ ${detail.correctAnswer}"
                        else "✗ ${detail.userAnswer ?: "-"} → ${detail.correctAnswer}",
                        color = if (isCorrect) Color.Green else Color.Red,
                        fontSize = 20.sp
                    )
                }
            }
        }
        Button(onClick = {
            // Повторить упражнения
            navController.navigate("exercise_pronoun_enter_screen") {
                popUpTo("exercises_screen") { inclusive = false }
            }
        }) {
            Text("Повторить")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Вернуться на выбор упражнений
            navController.popBackStack("exercises_pronouns_screen", false)
        }) {
            Text("Назад")
        }
    }
        ConfettiEffect(
            modifier = Modifier.fillMaxSize(),
            play = playConfetti
        )
    }
}