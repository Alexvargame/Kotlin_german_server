package com.example.german_server.ui.navigation.exercises.adjective

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.adjective.ExerciseAdjectiveKomparativSuperlativRepotory
import com.example.german_server.data.repository.exercises.adjective.ExerciseAdjectiveKomparativSuperlativViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveKomparativSuperlativViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.adjective.ExerciseAdjectiveKomparativSuperlativScreen
import android.util.Log


fun NavGraphBuilder.exercisesAdjectiveKomparativSuperlativNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("WORD_PAIR_", "Navigation")
    composable("exercise_adjective_komparativ_superlativ_screen") {
        Log.e("ARTICEL_", "Navigation_repo")
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)

        val repo = ExerciseAdjectiveKomparativSuperlativRepotory(
            adjectiveDao =  db.adjectiveDao(),
        )
        val viewModel: ExercisesAdjectiveKomparativSuperlativViewModel =
            viewModel(factory = ExerciseAdjectiveKomparativSuperlativViewModelFactory(repo))
        ExerciseAdjectiveKomparativSuperlativScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
