package com.example.german.ui.screens.exercises.verbs

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

import com.example.german.data.ui.viewModel.user_profile.UserProfileViewModel

@Composable
fun ExerciseVerbPrateritumFormResultScreen(
    correctCount: Int,
    totalQuestions: Int,
    navController: NavController,
    userProfileViewModel: UserProfileViewModel
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
        Text("Имя: ${user?.username ?: 0}", color = Color.Blue)
        Text("❤️ ${user?.lifes ?: 0}", color = Color.Red)
        Text("Баллы: ${user?.score ?: 0}", color = Color.Green)
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // Повторить упражнения
            navController.navigate("exercise_verb_prateritum_form_screen") {
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
}