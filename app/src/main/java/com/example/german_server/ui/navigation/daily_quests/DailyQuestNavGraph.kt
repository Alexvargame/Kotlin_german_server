package com.example.german_server.ui.navigation.daily_quests


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.viewModel.daily_quests.DailyQuestViewModel

import com.example.german_server.ui.screens.daily_quests.DailyQuestScreen

fun NavGraphBuilder.dailyQuestNavGraph (
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    dailyQuestViewModel: DailyQuestViewModel)
{
    composable("daily_quests_screen")
    {
        DailyQuestScreen(dailyQuestViewModel, userProfileViewModel,navController,)
    }
}
