package com.example.german_server.ui.navigation.exercises.adjective

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.adjective.ExerciseDeclensionsRepository
import com.example.german_server.data.repository.exercises.adjective.ExerciseAdjectiveDeclensionsViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveDeclensionsViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.adjective.ExerciseAdjectiveDeclensionsScreen
import android.util.Log


fun NavGraphBuilder.exercisesAdjectiveDeclensionsNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("Declen_", "Navigation")
    composable("exercise_adjective_declensions_screen") {
        Log.e("Declen_", "Navigation_repo")
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)

        val repo = ExerciseDeclensionsRepository(
            adjectiveDao =  db.adjectiveDao(),
        )
        val viewModel: ExercisesAdjectiveDeclensionsViewModel =
            viewModel(factory = ExerciseAdjectiveDeclensionsViewModelFactory(repo))
        ExerciseAdjectiveDeclensionsScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
