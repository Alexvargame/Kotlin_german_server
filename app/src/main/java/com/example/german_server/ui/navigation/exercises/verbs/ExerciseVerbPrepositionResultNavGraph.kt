package com.example.german_server.ui.navigation.exercises.verbs


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.verbs.ExerciseVerbPrepositionResultScreen
import android.util.Log
import androidx.compose.runtime.remember
import com.example.german_server.data.entities.exercises.VerbPrepositionAnswerDetail
import com.example.german_server.data.ui.viewModel.exercises.verb.ExercisesVerbPrepositionViewModel


fun NavGraphBuilder.exercisesVerbPrepositionResultNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("VERB_", "NavigationREsult")
    composable(
        "exercise_verb_preposition_result_screen/{correctCount}/{totalQuestions}",
        arguments = listOf(
            navArgument("correctCount") { type = NavType.IntType },
            navArgument("totalQuestions") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val correctCount = backStackEntry.arguments?.getInt("correctCount") ?: 0
        val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions") ?: 0
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry("exercise_verb_preposition_screen")
        }
        val exercisesViewModel: ExercisesVerbPrepositionViewModel = viewModel(parentEntry)
        ExerciseVerbPrepositionResultScreen(
            correctCount = correctCount,
            totalQuestions = totalQuestions,
            details = exercisesViewModel.lastResult?.details ?: emptyList(),
            navController = navController,
            userProfileViewModel = userProfileViewModel
        )
    }
}
