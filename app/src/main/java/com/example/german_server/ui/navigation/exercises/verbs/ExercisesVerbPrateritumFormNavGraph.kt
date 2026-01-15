package com.example.german_server.ui.navigation.exercises.verbs

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.verb.ExerciseVerbPrateritumFormRepoRepository
import com.example.german_server.data.repository.exercises.verb.ExerciseVerbPrateritumViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.verb.ExercisesVerbPrateritumViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.verbs.ExerciseVerbPrateritumFormScreen


fun NavGraphBuilder.exercisesVerbPrateritumFormNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
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
