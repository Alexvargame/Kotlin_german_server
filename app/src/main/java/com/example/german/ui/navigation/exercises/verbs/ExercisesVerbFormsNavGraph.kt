package com.example.german.ui.navigation.exercises.verbs

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german.data.AppDatabase
import com.example.german.data.repository.exercises.verb.ExercisesVerbFormsViewModelFactory
import com.example.german.data.ui.viewModel.exercises.verb.ExercisesVerbFormsViewModel
import com.example.german.data.ui.viewModel.user_profile.UserViewModel
import com.example.german.ui.screens.exercises.verbs.Exercises_verb_forms_screen


fun NavGraphBuilder.exercisesVerbFormsNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{
    composable("exercises_verb_forms_screen") {
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)

        val viewModel: ExercisesVerbFormsViewModel =
            viewModel(factory = ExercisesVerbFormsViewModelFactory(db))

        Exercises_verb_forms_screen(
            navController = navController,
            viewModel = viewModel
        )
    }
}
