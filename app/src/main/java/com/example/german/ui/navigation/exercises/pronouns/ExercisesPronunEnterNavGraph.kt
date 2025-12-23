package com.example.german.ui.navigation.exercises.pronouns

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german.data.AppDatabase
import com.example.german.data.repository.exercises.pronoun.ExercisePronounEnterRepoRepository
import com.example.german.data.repository.exercises.pronoun.ExercisePronounEnterViewModelFactory
import com.example.german.data.ui.viewModel.exercises.pronoun.ExercisesPronounEnterViewModel
import com.example.german.data.ui.viewModel.user_profile.UserProfileViewModel
import com.example.german.ui.screens.exercises.pronouns.ExercisePronounEnterScreen


fun NavGraphBuilder.exercisesPronounEnterNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserProfileViewModel,
    )
{
    Log.e("Pronoun_", "Navigation")
    composable("exercise_pronoun_enter_screen") {
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExercisePronounEnterRepoRepository(
            pronounDao = db.pronounDao(),
        )
        val viewModel: ExercisesPronounEnterViewModel =
            viewModel(factory = ExercisePronounEnterViewModelFactory(repo))

        ExercisePronounEnterScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
