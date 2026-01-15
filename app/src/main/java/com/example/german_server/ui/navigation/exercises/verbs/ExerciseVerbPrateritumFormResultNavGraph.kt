package com.example.german_server.ui.navigation.exercises.verbs


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.verbs.ExerciseVerbPrateritumFormResultScreen
import android.util.Log



fun NavGraphBuilder.exercisesVerbPrateritumFormResultNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("VERB_PRAT_RES", "NavigationREsult")
    composable(
        "exercise_verb_prateritum_form_result_screen/{correctCount}/{totalQuestions}",
        arguments = listOf(
            navArgument("correctCount") { type = NavType.IntType },
            navArgument("totalQuestions") { type = NavType.IntType }
        )
    ) { backStackEntry ->
        val correctCount = backStackEntry.arguments?.getInt("correctCount") ?: 0
        val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions") ?: 0
        ExerciseVerbPrateritumFormResultScreen(
            correctCount = correctCount,
            totalQuestions = totalQuestions,
            navController = navController,
            userProfileViewModel = userProfileViewModel
        )
    }
}
