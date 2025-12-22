package com.example.german.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.german.data.ui.viewModel.user_profile.UserProfileViewModel
import com.example.german.ui.navigation.exercises.exercisesNavGraph
import com.example.german.ui.navigation.exercises.exercisesDigitTranslateNavGraph
import com.example.german.ui.navigation.exercises.exercisesDigitTranslateResultNavGraph
import com.example.german.ui.navigation.exercises.exercisesWordsPairNavGraph
import com.example.german.ui.navigation.exercises.exerciseArticleNavGraph
import com.example.german.ui.navigation.exercises.exercisesArticleResultNavGraph
import com.example.german.ui.navigation.exercises.exercisesVerbFormsNavGraph
import com.example.german.ui.navigation.exercises.exercisesVerbPresentFormNavGraph
import com.example.german.ui.navigation.exercises.exercisesVerbPresentFormResultNavGraph
import com.example.german.ui.navigation.exercises.exercisesVerbPrateritumFormNavGraph
import com.example.german.ui.navigation.exercises.exercisesVerbPrateritumFormResultNavGraph
import com.example.german.ui.navigation.exercises.exercisesVerbPerfectFormNavGraph
import com.example.german.ui.navigation.exercises.exercisesVerbPerfectFormResultNavGraph

import com.example.german.ui.screens.HomeScreen


@Composable
fun appNavGraph(navController: NavHostController, userProfileViewModel: UserProfileViewModel,
               greetingText: String) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController, greetingText = greetingText)
        }
        authRegNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel
        )
        userProfileNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesDigitTranslateNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesDigitTranslateResultNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesWordsPairNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exerciseArticleNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesArticleResultNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesVerbFormsNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesVerbPresentFormNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesVerbPresentFormResultNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesVerbPrateritumFormNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesVerbPrateritumFormResultNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesVerbPerfectFormNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesVerbPerfectFormResultNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
    }
}
