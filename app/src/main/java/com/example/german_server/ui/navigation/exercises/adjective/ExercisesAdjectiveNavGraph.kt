package com.example.german_server.ui.navigation.exercises.adjective

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.adjective.ExercisesAdjectiveViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.adjective.Exercises_adjectives_screen


fun NavGraphBuilder.exercisesAdjectiveNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{
    composable("exercises_adjective_screen") {
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)

        val viewModel: ExercisesAdjectiveViewModel =
            viewModel(factory = ExercisesAdjectiveViewModelFactory(db))

        Exercises_adjectives_screen(
            navController = navController,
            viewModel = viewModel
        )
    }
}
