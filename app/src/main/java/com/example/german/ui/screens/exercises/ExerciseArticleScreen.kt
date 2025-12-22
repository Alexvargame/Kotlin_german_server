package com.example.german.ui.screens.exercises

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


import android.util.Log


import androidx.navigation.NavController

import com.example.german.data.ui.viewModel.user_profile.UserProfileViewModel
import com.example.german.data.ui.viewModel.exercises.ExercisesArticleViewModel


@Composable
fun ExerciseArticleScreen(
    navController: NavController,
    userProfileViewModel: UserProfileViewModel,
    viewModel: ExercisesArticleViewModel,

) {
    LaunchedEffect(Unit) {
        viewModel.loadExercises()
        viewModel.exercises.forEach { ex ->
            Log.d("ARTICLE_EXERCISE_LOG", "WordId: ${ex.word}, ArticleId: ${ex.article}, Variants: ${ex.variantsAnswer.map { it.name }}")
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
            text = "Упражнение: Правильный артикль",
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

        Spacer(modifier = Modifier.height(24.dp))

        exercises.forEachIndexed { index, ex ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(text = ex.word,
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
                            onClick = { viewModel.selectAnswer(index, variant.id.toLong()) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (ex.selectedOption == variant.id.toLong()) Color.Green else Color.LightGray,
                            )
                        ) {
                            Text("${variant.name}",
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

                // 3️⃣ Навигация на экран результатов
                navController.navigate(
                    "exercise_article_result_screen/${result.correctCount}/${result.totalQuestions}"
                )

            },
            modifier = Modifier.fillMaxWidth()
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
