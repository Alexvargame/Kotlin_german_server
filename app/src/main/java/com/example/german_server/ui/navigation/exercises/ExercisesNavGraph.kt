package com.example.german_server.ui.navigation.exercises

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.ExercisesViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.ExercisesViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.Exercises_screen


fun NavGraphBuilder.exercisesNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{
    composable("exercises_screen") {
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)

        val viewModel: ExercisesViewModel =
            viewModel(factory = ExercisesViewModelFactory(db))

        Exercises_screen(
            navController = navController,
            viewModel = viewModel
        )
    }
}
