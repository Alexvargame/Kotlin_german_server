package com.example.german.ui.navigation.exercises.verbs

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german.data.AppDatabase
import com.example.german.data.repository.exercises.verb.ExerciseVerbPresentFormRepoRepository
import com.example.german.data.repository.exercises.verb.ExerciseVerbPresentViewModelFactory
import com.example.german.data.ui.viewModel.exercises.verb.ExercisesVerbPresentViewModel
import com.example.german.data.ui.viewModel.user_profile.UserViewModel
import com.example.german.ui.screens.exercises.verbs.ExerciseVerbPresentFormScreen


fun NavGraphBuilder.exercisesVerbPresentFormNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{
    Log.e("VERB_PRES_", "Navigation")
    composable("exercise_verb_present_form_screen") {
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExerciseVerbPresentFormRepoRepository(
            verbDao = db.verbDao(),
        )
        val viewModel: ExercisesVerbPresentViewModel =
            viewModel(factory = ExerciseVerbPresentViewModelFactory(repo))

        ExerciseVerbPresentFormScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
