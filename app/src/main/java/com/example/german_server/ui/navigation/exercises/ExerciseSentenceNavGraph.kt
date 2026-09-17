package com.example.german_server.ui.navigation.exercises

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.ExerciseSentenceRepository
import com.example.german_server.data.repository.exercises.ExercisesViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.ExercisesSentenceViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.ExerciseArticleScreen
import android.util.Log
import com.example.german_server.data.repository.exercises.verb.ExerciseSentenceViewModelFactory
import com.example.german_server.data.ui.screens.exercises.ExerciseSentenceScreen


fun NavGraphBuilder.exerciseSentenceNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("WORD_PAIR_", "Navigation")
    composable("exercise_sentence_screen") {
        Log.e("ARTICEL_", "Navigation_repo")
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExerciseSentenceRepository(
            sentenceDao = db.sentenceDao(),
        )
        val viewModel: ExercisesSentenceViewModel =
            viewModel(factory = ExerciseSentenceViewModelFactory(repo))
        ExerciseSentenceScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
