package com.example.german_server.ui.navigation.exercises.adjective

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.exercises.adjective.ExerciseAdjectiveCasusRepository
import com.example.german_server.data.repository.exercises.adjective.ExerciseAdjectiveCasusViewModelFactory
import com.example.german_server.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveCasusViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.exercises.adjective.ExerciseAdjectiveCasusScreen
import android.util.Log


fun NavGraphBuilder.exercisesAdjectiveCasusNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    )
{   Log.e("WORD_PAIR_", "Navigation")
    composable("exercise_adjective_casus_screen") {
        Log.e("ARTICEL_", "Navigation_repo")
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)

        val repo = ExerciseAdjectiveCasusRepository(
            nounDao = db.nounDao(),
            nounDeclensionsFormDao = db.nounDeclensionsFormDao(),
            adjectiveDao =  db.adjectiveDao(),
            articleDao =  db.articleDao(),

        )
        val viewModel: ExercisesAdjectiveCasusViewModel =
            viewModel(factory = ExerciseAdjectiveCasusViewModelFactory(repo))
        ExerciseAdjectiveCasusScreen(
            navController,
            userProfileViewModel,
            viewModel,
        )
    }
}
