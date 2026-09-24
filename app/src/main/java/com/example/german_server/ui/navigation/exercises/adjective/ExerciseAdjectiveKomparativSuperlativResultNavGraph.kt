package com.example.german_server.ui.navigation.exercises.adjective


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.adjective.ExerciseAdjectiveKomparativSuperlativResultScreen
import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.german_server.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveDeclensionsViewModel
import com.example.german_server.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveKomparativSuperlativViewModel


fun NavGraphBuilder.exercisesAdjectiveKomparativSuperlativResultNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("KOMP_", "NavigationREsult")
    composable(
        "exercise_adjective_komparativ_superlativ_result_screen/{correctCount}/{totalQuestions}",
        arguments = listOf(
            navArgument("correctCount") { type = NavType.IntType },
            navArgument("totalQuestions") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val correctCount = backStackEntry.arguments?.getInt("correctCount") ?: 0
        val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions") ?: 0
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry("exercise_adjective_komparativ_superlativ_screen")
        }
        val exercisesViewModel: ExercisesAdjectiveKomparativSuperlativViewModel = viewModel(parentEntry)
        ExerciseAdjectiveKomparativSuperlativResultScreen(
            correctCount = correctCount,
            totalQuestions = totalQuestions,
            details = exercisesViewModel.lastResult?.details ?: emptyList(),
            navController = navController,
            userProfileViewModel = userProfileViewModel
        )
    }
}
