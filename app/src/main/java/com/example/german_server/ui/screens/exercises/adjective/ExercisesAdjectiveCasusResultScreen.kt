package com.example.german_server.ui.screens.exercises.adjective

import android.util.Log
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
import com.example.german_server.data.ui.components.UserStatsBlock

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel

@Composable
fun ExerciseAdjectiveCasusResultScreen(
    correctCount: Int,
    totalQuestions: Int,
    navController: NavController,
    userProfileViewModel: UserViewModel
) {
    val user = userProfileViewModel.currentUser.value
    Log.d("USER_SCXREEN_RESULT1","setUser -> ${user}")
    Log.d("USER_correct_RESULT1","setUser -> ${correctCount}")
    Log.d("USER_toalt_RESULT1","setUser -> ${totalQuestions}")
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
            navController.navigate("exercise_adjective_casus_screen") {
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