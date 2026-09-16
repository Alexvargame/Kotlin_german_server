package com.example.german_server.ui.navigation.exercises.verbs

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.verb.ExerciseVerbPrepositionRepository
import com.example.german_server.data.repository.exercises.verb.ExerciseVerbPrepositionViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.verb.ExercisesVerbPrepositionViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.verbs.ExercisesVerbPrepositionScreen
import android.util.Log



fun NavGraphBuilder.exercisesVerbPrepositionNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("WORD_PAIR_", "Navigation")
    composable("exercise_verb_preposition_screen") {
        Log.e("ARTICEL_", "Navigation_repo")
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExerciseVerbPrepositionRepository(
            verbDao = db.verbDao(),
        )
        val viewModel: ExercisesVerbPrepositionViewModel =
            viewModel(factory = ExerciseVerbPrepositionViewModelFactory(repo))
        ExercisesVerbPrepositionScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
