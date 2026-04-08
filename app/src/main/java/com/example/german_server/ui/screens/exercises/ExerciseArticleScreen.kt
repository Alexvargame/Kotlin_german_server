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


import android.util.Log


import androidx.navigation.NavController
import com.example.german_server.data.ui.components.UserStatsBlock

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.viewModel.exercises.ExercisesArticleViewModel

@Composable
fun ExerciseArticleScreen(
    navController: NavController,
    userProfileViewModel: UserViewModel,
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

    Log.e("USER_after_screen", "${userProfileViewModel}")
    Log.e("USER_user", "${user}")

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
                UserStatsBlock(u, userProfileViewModel)
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

                if (result.wrongCount > 0) {
                    userProfileViewModel.decreaseLife()
                }
                Log.d("USER_SCXREEN_DECREASE","setUser -> ${user}")
                userProfileViewModel.addScore(result.correctCount)
                userProfileViewModel.updateShockMod()
                userProfileViewModel.updateQuestsAfterExercise(result.correctCount, result.wrongCount)
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

/*@Composable
fun ExerciseArticleScreen(
    navController: NavController,
    userProfileViewModel: UserViewModel,
    viewModel: ExercisesArticleViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.loadExercises()
    }

    val user = userProfileViewModel.currentUser.value
    val exercises = viewModel.exercises

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // 👉 внешний отступ всего экрана
            .verticalScroll(rememberScrollState())
    ) {

        // ================== ЗАГОЛОВОК ==================
        Text(
            text = "Упражнение: Правильный артикль",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // ================== КАРТОЧКА СТАТИСТИКИ ==================
        user?.let { u ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
                // 👉 elevation = визуальная «тень», сразу +100 к качеству
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), // 👉 воздух внутри карточки
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    UserStatsBlock(u)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ================== КАРТОЧКИ УПРАЖНЕНИЙ ==================
        exercises.forEachIndexed { index, ex ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp), // 👉 расстояние между карточками
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
                // 👉 карточка «приподнята» над фоном
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                    // 👉 ВАЖНО: padding внутри Card, а не background
                ) {

                    // ---------- СЛОВО ----------
                    Text(
                        text = ex.word,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // ---------- КНОПКИ ВАРИАНТОВ ----------
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                        // 👉 spacedBy вместо SpaceEvenly — аккуратнее визуально
                    ) {
                        ex.variantsAnswer.forEach { variant ->

                            val isSelected =
                                ex.selectedOption == variant.id.toLong()

                            Button(
                                onClick = {
                                    viewModel.selectAnswer(
                                        index,
                                        variant.id.toLong()
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                // 👉 все кнопки одинаковой ширины
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                        if (isSelected)
                                            Color(0xFF4CAF50) // зелёный выбранный
                                        else
                                            Color(0xFFE0E0E0) // нейтральный
                                )
                            ) {
                                Text(
                                    text = variant.name,
                                    color =
                                        if (isSelected)
                                            Color.White
                                        else
                                            Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ================== КАРТОЧКА ДЕЙСТВИЙ ==================
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = {
                        val result = viewModel.checkAnswers()

                        if (result.wrongCount > 0) {
                            userProfileViewModel.decreaseLife()
                        }
                        userProfileViewModel.addScore(result.correctCount)
                        userProfileViewModel.updateShockMod()

                        navController.navigate(
                            "exercise_article_result_screen/${result.correctCount}/${result.totalQuestions}"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Проверить")
                }

                Button(
                    onClick = {
                        navController.popBackStack(
                            "exercises_screen",
                            false
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Назад")
                }
            }
        }
    }
}
*/