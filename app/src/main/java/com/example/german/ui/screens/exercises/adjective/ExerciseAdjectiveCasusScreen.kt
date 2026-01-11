package com.example.german.ui.screens.exercises.adjective

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


import android.util.Log


import androidx.navigation.NavController
import com.example.german.data.ui.components.UserStatsBlock


import com.example.german.data.ui.viewModel.user_profile.UserViewModel
import com.example.german.data.ui.viewModel.exercises.adjective.ColumnTypeAdjective
import com.example.german.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveCasusViewModel



@Composable
fun ExerciseAdjectiveCasusScreen(
    navController: NavController,
    userProfileViewModel: UserViewModel,
    viewModel: ExercisesAdjectiveCasusViewModel,

    ) {
    LaunchedEffect(Unit) {
        viewModel.loadExercises()
    }
    val user = userProfileViewModel.currentUser.value
    val articleButtons = viewModel.articleButtons
    val adjectiveButtons = viewModel.adjectiveButtons
    val nounButtons = viewModel.nounButtons
    val exercise = viewModel.exercise

    Log.e("USER_after_screen", "${exercise}")
    Log.e("USER_user", "${user}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Упражнение: Артикли, прилагательные",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        user?.let { u ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                UserStatsBlock(u)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "${exercise?.question}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Колонка артиклей
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                articleButtons.forEach { btn ->
                    Button(
                        onClick = { viewModel.selectAnswer(ColumnTypeAdjective.ARTICLE, btn.value) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when {
                                !btn.isSelected -> Color.LightGray
                                btn.isCorrect == true -> Color(0xFF4CAF50)
                                btn.isCorrect == false -> Color(0xFFF44336)
                                else -> Color.LightGray
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(btn.value)
                    }
                }
            }

            // Колонка прилагательных
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                adjectiveButtons.forEach { btn ->
                    Button(
                        onClick = { viewModel.selectAnswer(ColumnTypeAdjective.ADJECTIVE, btn.value) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when {
                                !btn.isSelected -> Color.LightGray
                                btn.isCorrect == true -> Color(0xFF4CAF50)
                                btn.isCorrect == false -> Color(0xFFF44336)
                                else -> Color.LightGray
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(btn.value)
                    }
                }
            }

            // Колонка существительных
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                nounButtons.forEach { btn ->
                    Button(
                        onClick = { viewModel.selectAnswer(ColumnTypeAdjective.NOUN, btn.value) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when {
                                !btn.isSelected -> Color.LightGray
                                btn.isCorrect == true -> Color(0xFF4CAF50)
                                btn.isCorrect == false -> Color(0xFFF44336)
                                else -> Color.LightGray
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(btn.value)
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(24.dp))

            if (viewModel.isAllCorrect()) {
                Button(
                    onClick = {
                        val result = viewModel.checkAnswers()
                        if (result.wrongCount > 0) {
                            userProfileViewModel.decreaseLife()
                        }
                        userProfileViewModel.addScore(result.correctCount)

                        navController.navigate(
                            "exercise_adjective_casus_result_screen/${result.correctCount}/${result.totalQuestions}"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Проверить")
                }
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
