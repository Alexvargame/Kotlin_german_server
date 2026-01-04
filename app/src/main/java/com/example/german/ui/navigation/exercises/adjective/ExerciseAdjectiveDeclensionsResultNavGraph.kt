package com.example.german.ui.navigation.exercises.adjective


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.example.german.data.ui.viewModel.user_profile.UserProfileViewModel
import com.example.german.ui.screens.exercises.adjective.ExerciseAdjectiveDeclensionsResultScreen
import android.util.Log



fun NavGraphBuilder.exercisesAdjectiveDeclensionsResultNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserProfileViewModel,
    )
{   Log.e("KOMP_", "NavigationREsult")
    composable(
        "exercise_adjective_declensions_result_screen/{correctCount}/{totalQuestions}",
        arguments = listOf(
            navArgument("correctCount") { type = NavType.IntType },
            navArgument("totalQuestions") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val correctCount = backStackEntry.arguments?.getInt("correctCount") ?: 0
        val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions") ?: 0
        ExerciseAdjectiveDeclensionsResultScreen(
            correctCount = correctCount,
            totalQuestions = totalQuestions,
            navController = navController,
            userProfileViewModel = userProfileViewModel
        )
    }
}
