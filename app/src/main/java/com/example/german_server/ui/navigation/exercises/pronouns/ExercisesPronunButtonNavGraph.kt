package com.example.german_server.ui.navigation.exercises.pronouns

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.pronoun.ExercisePronounButtonRepoRepository
import com.example.german_server.data.repository.exercises.pronoun.ExercisePronounButtonViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.pronoun.ExercisesPronounButtonViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.pronouns.ExercisePronounButtonScreen


fun NavGraphBuilder.exercisesPronounButtonNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{
    Log.e("Pronoun_", "Navigation")
    composable("exercise_pronoun_button_screen") {
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExercisePronounButtonRepoRepository(
            pronounDao = db.pronounDao(),
        )
        val viewModel: ExercisesPronounButtonViewModel =
            viewModel(factory = ExercisePronounButtonViewModelFactory(repo))

        ExercisePronounButtonScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
