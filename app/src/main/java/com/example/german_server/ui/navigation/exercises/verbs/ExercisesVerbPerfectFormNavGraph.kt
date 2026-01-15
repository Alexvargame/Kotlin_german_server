package com.example.german_server.ui.navigation.exercises.verbs

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.verb.ExerciseVerbPerfectFormRepoRepository
import com.example.german_server.data.repository.exercises.verb.ExerciseVerbPerfectViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.verb.ExercisesVerbPerfectViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.verbs.ExerciseVerbPerfectFormScreen

import android.util.Log

fun NavGraphBuilder.exercisesVerbPerfectFormNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{
    Log.e("VERB_PERF", "Navigation")
    composable("exercise_verb_perfect_form_screen") {
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExerciseVerbPerfectFormRepoRepository(
            verbDao = db.verbDao(),
        )
        val viewModel: ExercisesVerbPerfectViewModel =
            viewModel(factory = ExerciseVerbPerfectViewModelFactory(repo))

        ExerciseVerbPerfectFormScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
