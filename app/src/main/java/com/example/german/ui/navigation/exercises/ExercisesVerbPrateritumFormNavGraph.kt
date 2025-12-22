package com.example.german.ui.navigation.exercises

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german.data.AppDatabase
import com.example.german.data.repository.exercises.ExerciseVerbPrateritumFormRepoRepository
import com.example.german.data.repository.exercises.ExerciseVerbPrateritumViewModelFactory
import com.example.german.data.ui.viewModel.exercises.ExercisesVerbPrateritumViewModel
import com.example.german.data.ui.viewModel.user_profile.UserProfileViewModel
import com.example.german.ui.screens.exercises.ExerciseVerbPrateritumFormScreen


fun NavGraphBuilder.exercisesVerbPrateritumFormNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserProfileViewModel,
    )
{
    Log.e("VERB_PRAT", "Navigation")
    composable("exercise_verb_prateritum_form_screen") {
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExerciseVerbPrateritumFormRepoRepository(
            verbDao = db.verbDao(),
        )
        val viewModel: ExercisesVerbPrateritumViewModel =
            viewModel(factory = ExerciseVerbPrateritumViewModelFactory(repo))

        ExerciseVerbPrateritumFormScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
