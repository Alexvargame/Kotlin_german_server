package com.example.german_server.ui.screens.exercises.verbs

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
import com.example.german_server.data.ui.viewModel.exercises.verb.ExercisesVerbPrepositionViewModel

@Composable
fun ExercisesVerbPrepositionScreen(
    navController: NavController,
    userProfileViewModel: UserViewModel,
    viewModel: ExercisesVerbPrepositionViewModel,

    ) {
    LaunchedEffect(Unit) {
        viewModel.loadExercises()
        viewModel.exercises.forEach { ex ->
            Log.d("ARTICLE_EXERCISE_LOG", "WordId: ${ex.word}, ArticleId: ${ex.preposition}," +
                    " Variants: ${ex.variantsAnswer}")
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
            text = "Упражнение: Правильный артикль и предлог",
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
                    .background(Color(0xFFFFF8E1), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(text = ex.word,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color(0xFF1976D2)
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
                                containerColor = if (ex.selectedOption == variant) Color(0xFF388E3C) else Color.DarkGray,
                            )
                        ) {
                            Text("${variant}",
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
                    "exercise_verb_preposition_result_screen/${result.correctCount}/${result.totalQuestions}"
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

