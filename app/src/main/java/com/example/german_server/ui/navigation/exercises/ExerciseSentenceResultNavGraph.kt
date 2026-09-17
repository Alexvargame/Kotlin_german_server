package com.example.german_server.ui.navigation.exercises


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.ExerciseSentenceResultScreen
import android.util.Log
import android.net.Uri


fun NavGraphBuilder.exerciseSentenceResultNavGraph(

    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    ) {
    Log.e("SENTENCE_", "NavigationResult")
    composable(
        "exercise_sentence_result_screen/{isCorrect}/{userAnswer}/{correctAnswer}/{translation}/{description}",
        arguments = listOf(
            navArgument("isCorrect") { type = NavType.BoolType },
            navArgument("userAnswer") { type = NavType.StringType },
            navArgument("correctAnswer") { type = NavType.StringType },
            navArgument("translation") { type = NavType.StringType },
            navArgument("description") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val isCorrect = backStackEntry.arguments?.getBoolean("isCorrect") ?: false
        val userAnswer = Uri.decode(backStackEntry.arguments?.getString("userAnswer") ?: "")
        val correctAnswer = Uri.decode(backStackEntry.arguments?.getString("correctAnswer") ?: "")
        val translation = Uri.decode(backStackEntry.arguments?.getString("translation") ?: "")
        val description = Uri.decode(backStackEntry.arguments?.getString("description") ?: "")

        ExerciseSentenceResultScreen(
            isCorrect = isCorrect,
            userAnswer = userAnswer,
            correctAnswer = correctAnswer,
            translation = translation,
            description = description,
            navController = navController,
            userProfileViewModel = userProfileViewModel
        )
    }

}