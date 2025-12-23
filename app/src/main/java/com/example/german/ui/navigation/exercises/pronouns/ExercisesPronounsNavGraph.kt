package com.example.german.ui.navigation.exercises.pronouns

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german.data.AppDatabase
import com.example.german.data.repository.exercises.pronoun.ExercisesPronounsViewModelFactory
import com.example.german.data.ui.viewModel.exercises.pronoun.ExercisesPronounsViewModel
import com.example.german.data.ui.viewModel.user_profile.UserProfileViewModel
import com.example.german.ui.screens.exercises.pronouns.Exercises_pronouns_screen


fun NavGraphBuilder.exercisesPronounsNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserProfileViewModel,
    )
{
    composable("exercises_pronouns_screen") {
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)

        val viewModel: ExercisesPronounsViewModel =
            viewModel(factory = ExercisesPronounsViewModelFactory(db))

        Exercises_pronouns_screen(
            navController = navController,
            viewModel = viewModel
        )
    }
}
