package com.example.german_server.ui.screens.exercises

import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
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

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.viewModel.exercises.ExercisesDigitTranslateViewModel
import com.example.german_server.data.ui.components.UserStatsBlock

@Composable
fun ExerciseDigitTranslateScreen(
    navController: NavController,
    userProfileViewModel: UserViewModel,
    viewModel: ExercisesDigitTranslateViewModel,

    ) {
    LaunchedEffect(Unit) {
        viewModel.loadExercises()
    }
    val user = userProfileViewModel.currentUser.value
    val exercises = viewModel.exercises



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Упражнение: Перевод числительных",
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

        exercises.forEachIndexed { index, ex ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(text = ex.germanTranslate,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    ex.variantsAnswer.forEach { variant ->
                        Button(
                            onClick = { viewModel.selectAnswer(index, variant) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (ex.selectedOption == variant) Color.Green else Color.LightGray,
                            )
                        ) {
                            Text("$variant",
                                //color = if (ex.selectedOption == variant) Color.White else Color.Black,
                               // fontWeight = FontWeight.Bold
                            )
                        }
                    }

                }

            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val result = viewModel.checkAnswers()

// Можно для отладки
                println("Правильных: ${result.correctCount}")
                println("Неправильных: ${result.wrongCount}")
                println("Всего вопросов: ${result.totalQuestions}")

                if (result.wrongCount > 0) {
                    userProfileViewModel.decreaseLife()
                }
                userProfileViewModel.addScore(result.correctCount)
                userProfileViewModel.updateShockMod()
                // 3️⃣ Навигация на экран результатов
                navController.navigate(
                    "exercise_digit_translate_result_screen/${result.correctCount}/${result.totalQuestions}"
                )

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Проверить")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {navController.popBackStack("exercises_screen", false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}
