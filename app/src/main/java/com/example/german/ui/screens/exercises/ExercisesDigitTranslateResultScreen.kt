package com.example.german.ui.screens.exercises

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment


import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.Color

import androidx.navigation.NavController

import com.example.german.data.ui.viewModel.user_profile.UserViewModel
import com.example.german.data.ui.components.UserStatsBlock

@Composable
fun ExerciseDigitTranslateResultScreen(
    correctCount: Int,
    totalQuestions: Int,
    navController: NavController,
    userProfileViewModel: UserViewModel
) {
    val user = userProfileViewModel.currentUser.value

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Вы ответили на $correctCount из $totalQuestions вопросов",
            color=Color.White)
        Spacer(modifier = Modifier.height(16.dp))
        //Text("Очки: ${user?.score ?: 0}")
        //Text("Жизни: ${user?.lifes ?: 0}")
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
            navController.navigate("exercise_digit_translate_screen") {
                popUpTo("exercises_screen") { inclusive = false }
            }
        }) {
            Text("Повторить")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Вернуться на выбор упражнений
            navController.popBackStack("exercises_screen", false)
        }) {
            Text("Назад")
        }
    }
}