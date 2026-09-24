package com.example.german_server.ui.navigation.exercises.pronouns


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.pronouns.ExercisePronounButtonResultScreen
import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.german_server.data.ui.viewModel.exercises.ExercisesArticleViewModel
import com.example.german_server.data.ui.viewModel.exercises.pronoun.ExercisesPronounButtonViewModel


fun NavGraphBuilder.exercisesPronounButtonResultNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("Pronoun_enter", "NavigationREsult")
    composable(
        "exercise_pronoun_button_result_screen/{correctCount}/{totalQuestions}",
        arguments = listOf(
            navArgument("correctCount") { type = NavType.IntType },
            navArgument("totalQuestions") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val correctCount = backStackEntry.arguments?.getInt("correctCount") ?: 0
        val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions") ?: 0
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry("exercise_pronoun_button_screen")
        }
        val exercisesViewModel: ExercisesPronounButtonViewModel = viewModel(parentEntry)
        ExercisePronounButtonResultScreen(
            correctCount = correctCount,
            totalQuestions = totalQuestions,
            details = exercisesViewModel.lastResult?.details ?: emptyList(),
            navController = navController,
            userProfileViewModel = userProfileViewModel
        )
    }
}
