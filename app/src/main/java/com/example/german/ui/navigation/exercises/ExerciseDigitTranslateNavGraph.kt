package com.example.german.ui.navigation.exercises

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german.data.AppDatabase
import com.example.german.data.repository.exercises.ExerciseDigitTranslateRepository
import com.example.german.data.repository.exercises.ExerciseDigitTranslateViewModelFactory
import com.example.german.data.ui.viewModel.exercises.ExercisesDigitTranslateViewModel
import com.example.german.data.ui.viewModel.user_profile.UserViewModel
import com.example.german.ui.screens.exercises.ExerciseDigitTranslateScreen
import android.util.Log



fun NavGraphBuilder.exercisesDigitTranslateNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("DIGIT_", "Navigation")
    composable("exercise_digit_translate_screen") {
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val repo = ExerciseDigitTranslateRepository()
        val viewModel: ExercisesDigitTranslateViewModel =
            viewModel(factory = ExerciseDigitTranslateViewModelFactory(repo))
        ExerciseDigitTranslateScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
