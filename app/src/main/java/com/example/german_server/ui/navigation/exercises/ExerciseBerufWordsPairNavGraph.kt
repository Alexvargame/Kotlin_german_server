package com.example.german_server.ui.navigation.exercises

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.ExerciseBerufWordsPairRepository
import com.example.german_server.data.repository.exercises.ExerciseBerufWordsPairViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.ExercisesBerufWordsPairViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.ExerciseBerufWordsPairScreen
import android.util.Log



fun NavGraphBuilder.exerciseBerufWordsPairNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("WORD_PAIR_", "Navigation")
    composable("exercise_beruf_words_translate_screen") {
        Log.e("WORD_PAIR_", "Navigation_repo")
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExerciseBerufWordsPairRepository(
            nounDao = db.nounDao(),
        )
        val viewModel: ExercisesBerufWordsPairViewModel =
            viewModel(factory = ExerciseBerufWordsPairViewModelFactory(repo))
        ExerciseBerufWordsPairScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
