package com.example.german_server.ui.screens.exercises.adjective

import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf


import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.ui.text.TextStyle


import android.util.Log

import androidx.navigation.NavController
import com.example.german_server.data.ui.components.UserStatsBlock

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveKomparativSuperlativViewModel


@Composable
fun ExerciseAdjectiveKomparativSuperlativScreen(
    navController: NavController,
    userProfileViewModel: UserViewModel,
    viewModel: ExercisesAdjectiveKomparativSuperlativViewModel,

    ) {
    LaunchedEffect(Unit) {
        viewModel.loadExercises()
        viewModel.exercises.forEach { ex ->
            Log.d("Verb_present", "WordId: ${ex.word}")
        }
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
            text = "Упражнение: Напиши правильную" +
                    " форму прилагательного",
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
                // 1.
                Text(
                    text = ex.question,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))


                Spacer(modifier = Modifier.height(8.dp))

                // 3. Поле для ввода ответа
                var answer by remember { mutableStateOf(ex.userAnswer ?: "") }

                TextField(
                    value = answer,
                    onValueChange = { newValue ->
                        answer = newValue
                        ex.userAnswer = newValue // сохраняем в объекте упражнения
                    },
                    placeholder = { Text("Введите правильную форму") },
                    textStyle = TextStyle(color = Color.Gray), // тёмный текст
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White) // белый фон
                        .padding(4.dp)
                    //modifier = Modifier.fillMaxWidth(),

                )
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

                // 3️⃣ Навигация на экран результатов
                navController.navigate(
                    "exercise_adjective_komparativ_superlativ_result_screen/${result.correctCount}/${result.totalQuestions}"
                )

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Проверить")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navController.popBackStack("exercises_screen", false)},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
    }
}
