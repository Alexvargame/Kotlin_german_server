package com.example.german.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.german.data.ui.viewModel.user_profile.UserViewModel
import com.example.german.ui.navigation.exercises.exercisesNavGraph
import com.example.german.ui.navigation.exercises.exercisesDigitTranslateNavGraph
import com.example.german.ui.navigation.exercises.exercisesDigitTranslateResultNavGraph
import com.example.german.ui.navigation.exercises.exercisesWordsPairNavGraph
import com.example.german.ui.navigation.exercises.exerciseArticleNavGraph
import com.example.german.ui.navigation.exercises.exercisesArticleResultNavGraph
import com.example.german.ui.navigation.exercises.verbs.exercisesVerbFormsNavGraph
import com.example.german.ui.navigation.exercises.verbs.exercisesVerbPresentFormNavGraph
import com.example.german.ui.navigation.exercises.verbs.exercisesVerbPresentFormResultNavGraph
import com.example.german.ui.navigation.exercises.verbs.exercisesVerbPrateritumFormNavGraph
import com.example.german.ui.navigation.exercises.verbs.exercisesVerbPrateritumFormResultNavGraph
import com.example.german.ui.navigation.exercises.verbs.exercisesVerbPerfectFormNavGraph
import com.example.german.ui.navigation.exercises.verbs.exercisesVerbPerfectFormResultNavGraph
import com.example.german.ui.navigation.exercises.pronouns.exercisesPronounsNavGraph
import com.example.german.ui.navigation.exercises.pronouns.exercisesPronounEnterNavGraph
import com.example.german.ui.navigation.exercises.pronouns.exercisesPronounEnterResultNavGraph
import com.example.german.ui.navigation.exercises.pronouns.exercisesPronounButtonNavGraph
import com.example.german.ui.navigation.exercises.pronouns.exercisesPronounButtonResultNavGraph
import com.example.german.ui.navigation.exercises.adjective.exercisesAdjectiveNavGraph
import com.example.german.ui.navigation.exercises.adjective.exercisesAdjectiveCasusNavGraph
import com.example.german.ui.navigation.exercises.adjective.exercisesAdjectiveCasusResultNavGraph
import com.example.german.ui.navigation.exercises.adjective.exercisesAdjectiveKomparativSuperlativResultNavGraph
import com.example.german.ui.navigation.exercises.adjective.exercisesAdjectiveKomparativSuperlativNavGraph
import com.example.german.ui.navigation.exercises.adjective.exercisesAdjectiveDeclensionsNavGraph
import com.example.german.ui.navigation.exercises.adjective.exercisesAdjectiveDeclensionsResultNavGraph
import com.example.german.ui.navigation.user.userNavGraph
import com.example.german.ui.navigation.user.userProfileNavGraph
import com.example.german.ui.navigation.user.userProfileEditNavGraph

import com.example.german.ui.screens.HomeScreen


@Composable
fun appNavGraph(navController: NavHostController, userProfileViewModel: UserViewModel,
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
        userNavGraph(
                navController = navController,
        userProfileViewModel = userProfileViewModel,
        )
        userProfileNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        userProfileEditNavGraph(
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
        exercisesPronounsNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesPronounEnterNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesPronounEnterResultNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesPronounButtonNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesPronounButtonResultNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        backUpNavGraph(
            navController = navController,
        )
        exercisesAdjectiveNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesAdjectiveCasusNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesAdjectiveCasusResultNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesAdjectiveKomparativSuperlativNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesAdjectiveKomparativSuperlativResultNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesAdjectiveDeclensionsNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
        exercisesAdjectiveDeclensionsResultNavGraph(
            navController = navController,
            userProfileViewModel = userProfileViewModel,
        )
    }
}
