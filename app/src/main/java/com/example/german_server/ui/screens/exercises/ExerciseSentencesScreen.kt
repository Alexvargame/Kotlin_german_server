package com.example.german_server.data.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.german_server.data.ui.components.UserStatsBlock
import com.example.german_server.data.ui.viewModel.exercises.ExercisesSentenceViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi

import androidx.compose.runtime.key
import android.net.Uri

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseSentenceScreen(
    navController: NavController,
    userProfileViewModel: UserViewModel,
    viewModel: ExercisesSentenceViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.loadExercise()
    }

    val user = userProfileViewModel.currentUser.value
    val exercise = viewModel.exercise

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Упражнение: Собери предложение",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        user?.let { u ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                UserStatsBlock(u, userProfileViewModel)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        exercise?.let { ex ->
            // === ПОЛЕ РАССТАНОВКИ ===
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
                    .heightIn(min = 80.dp)
            ) {
                Text(
                    text = "Расставь слова:",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ex.userWords.forEachIndexed { index, word ->
                        key(word, index) {
                            Button(
                                onClick = { viewModel.removeWord(word) },
                                modifier = Modifier.height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.DarkGray,
//                                    contentColor = Color.Black  // ⬇️ ИСПРАВЛЕНИЕ 2
                                )
                            ) {
                                Text(word, color=Color.White, fontSize = 16.sp )  // ⬇️ убран color, берётся из contentColor
                            }
                        }
                    }
//                    ex.userWords.forEach { word ->
//                        Button(
//                            onClick = { viewModel.removeWord(word) },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor =Color.LightGray  //Color(0xFF2196F3)
//                            )
//                        ) {
//                            Text(word, color = Color.Black)
//                        }
//                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // === КНОПКИ СО СЛОВАМИ ===
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "Доступные слова:",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ex.availableWords.forEachIndexed { index, word ->
                        key(word, index) {
                            Button(
                                onClick = { viewModel.addWord(word) },
                                modifier = Modifier.height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.DarkGray,
//                                    contentColor = Color.Black  // ⬇️ ИСПРАВЛЕНИЕ 2
                                )
                            ) {
                                Text(word, color=Color.White,
                                        fontSize = 16.sp  )  // ⬇️ убран color
                            }
                        }
                    }
//                    ex.availableWords.forEach { word ->
//                        Button(
//                            onClick = { viewModel.addWord(word) },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = Color.LightGray
//                            )
//                        ) {
//                            Text(word, color = Color.DarkGray)
//                        }
//                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val result = viewModel.checkAnswer()
                if (!result.isCorrect) {
                    userProfileViewModel.decreaseLife()
                }
                userProfileViewModel.addScore(if (result.isCorrect) 1 else 0)
                userProfileViewModel.updateShockMod()
                userProfileViewModel.updateQuestsAfterExercise(
                    if (result.isCorrect) 1 else 0,
                    if (result.isCorrect) 0 else 1
                )
                navController.navigate(
                    "exercise_sentence_result_screen/" +
                            "${result.isCorrect}/" +
                            "${Uri.encode(result.userAnswer)}/" +
                            "${Uri.encode(result.correctAnswer)}/" +
                            "${Uri.encode(result.translation ?: "")}/" +
                            "${Uri.encode(result.description ?: "")}"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = exercise?.availableWords?.isEmpty() == true
        ) {
            Text("Проверить")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.popBackStack("exercises_screen", false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}