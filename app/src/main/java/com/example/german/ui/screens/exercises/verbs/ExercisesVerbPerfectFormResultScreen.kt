package com.example.german.ui.screens.exercises.verbs

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment


import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

import androidx.navigation.NavController
import com.example.german.data.ui.components.ConfettiEffect
import com.example.german.data.ui.components.UserStatsBlock

import com.example.german.data.ui.viewModel.user_profile.UserViewModel

@Composable
fun ExerciseVerbPerfectFormResultScreen(
    correctCount: Int,
    totalQuestions: Int,
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
                UserStatsBlock(u)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // Повторить упражнения
            navController.navigate("exercise_verb_perfect_form_screen") {
                popUpTo("exercises_screen") { inclusive = false }
            }
        }) {
            Text("Повторить")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Вернуться на выбор упражнений
            navController.popBackStack("exercises_verb_forms_screen", false)
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