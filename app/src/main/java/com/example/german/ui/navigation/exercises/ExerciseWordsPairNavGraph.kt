package com.example.german.ui.navigation.exercises

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german.data.AppDatabase
import com.example.german.data.repository.exercises.ExerciseWordsPairRepository
import com.example.german.data.repository.exercises.ExerciseWordsPairViewModelFactory
import com.example.german.data.ui.viewModel.exercises.ExercisesWordsPairViewModel
import com.example.german.data.ui.viewModel.user_profile.UserViewModel
import com.example.german.ui.screens.exercises.ExerciseWordsPairScreen
import android.util.Log



fun NavGraphBuilder.exercisesWordsPairNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("WORD_PAIR_", "Navigation")
    composable("exercise_words_translate_screen") {
        Log.e("WORD_PAIR_", "Navigation_repo")
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExerciseWordsPairRepository(
            nounDao = db.nounDao(),
            verbDao = db.verbDao(),
            adjectiveDao = db.adjectiveDao(),
            numeralDao = db.numeralDao(),
            pronounDao = db.pronounDao(),
            otherWordDao = db.otherWordDao()
        )
        val viewModel: ExercisesWordsPairViewModel =
            viewModel(factory = ExerciseWordsPairViewModelFactory(repo))
        ExerciseWordsPairScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
