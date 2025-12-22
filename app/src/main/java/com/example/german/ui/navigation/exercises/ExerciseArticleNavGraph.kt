package com.example.german.ui.navigation.exercises

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german.data.AppDatabase
import com.example.german.data.repository.exercises.ExerciseArticleRepository
import com.example.german.data.repository.exercises.ExerciseArticleViewModelFactory
import com.example.german.data.ui.viewModel.exercises.ExercisesArticleViewModel
import com.example.german.data.ui.viewModel.user_profile.UserProfileViewModel
import com.example.german.ui.screens.exercises.ExerciseArticleScreen
import android.util.Log



fun NavGraphBuilder.exerciseArticleNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserProfileViewModel,
    )
{   Log.e("WORD_PAIR_", "Navigation")
    composable("exercise_article_screen") {
        Log.e("ARTICEL_", "Navigation_repo")
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExerciseArticleRepository(
            nounDao = db.nounDao(),
        )
        val viewModel: ExercisesArticleViewModel =
            viewModel(factory = ExerciseArticleViewModelFactory(repo))
        ExerciseArticleScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
