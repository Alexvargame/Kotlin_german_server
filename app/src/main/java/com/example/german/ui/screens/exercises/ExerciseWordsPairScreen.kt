package com.example.german.ui.screens.exercises

import android.util.Log
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.LaunchedEffect



import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color


import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults



import androidx.navigation.NavController

import com.example.german.data.ui.viewModel.user_profile.UserViewModel
import com.example.german.data.ui.viewModel.exercises.ExercisesWordsPairViewModel
import com.example.german.data.ui.viewModel.exercises.ColumnType

@Composable
fun ExerciseWordsPairScreen(
    navController: NavController,
    userProfileViewModel: UserViewModel,
    viewModel: ExercisesWordsPairViewModel,

    ) {
    Log.e("WORD_PAIR_", "Screen")
    LaunchedEffect(Unit) {
        viewModel.loadExercises()
    }
    val user = userProfileViewModel.currentUser.value
    val leftButtons = viewModel.leftButtons
    val rightButtons = viewModel.rightButtons

    val exerciseFinished = viewModel.exerciseFinished


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        LaunchedEffect(exerciseFinished) {
            if (exerciseFinished) {
                userProfileViewModel.addScore(viewModel.leftButtons.size)
                if (viewModel.errorCount > 0 && viewModel.errorCount < 2) {
                    userProfileViewModel.decreaseLife()
                } else {
                    if (viewModel.errorCount > 1 && viewModel.errorCount < 4) {
                        userProfileViewModel.decreaseLife()
                        userProfileViewModel.decreaseLife()
                    } else {
                        userProfileViewModel.decreaseLife()
                        userProfileViewModel.decreaseLife()
                        userProfileViewModel.decreaseLife()
                    }
                }
            }
        }

        Text(
            text = "Упражнение: Найдите правильные пары",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        user?.let { u ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Имя: ${u.username}", color = Color.Blue)
                Text("❤️ ${u.lifes}", color = Color.Red)
                Text("Баллы: ${u.score}", color = Color.Green)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                leftButtons.forEach { btn ->
                    Button(
                        onClick = {
                            viewModel.onButtonClick(
                                wordId = btn.wordId,
                                column = ColumnType.LEFT
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = btn.color
                        )
                    ) {
                        Text(btn.text)
                    }
                }
            }
            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rightButtons.forEach { btn ->
                    Button(
                        onClick = {
                            viewModel.onButtonClick(
                                wordId = btn.wordId,
                                column = ColumnType.RIGHT
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = btn.color
                        )
                    ) {
                        Text(btn.text)
                    }
                }
            }



        }
        Spacer(modifier = Modifier.height(24.dp))

        Spacer(modifier = Modifier.height(12.dp))
        if (exerciseFinished) {
            Button(onClick = { viewModel.loadExercises() }) {
                Text("Повторить")
            }
        }

        Button(
            onClick = {navController.popBackStack("exercises_screen", false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}
