package com.example.german.ui.navigation


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder


import com.example.german.data.ui.viewModel.user_profile.UserProfileViewModel

import com.example.german.ui.screens.User_profile_screen

fun NavGraphBuilder.userProfileNavGraph (
    navController: NavHostController,
    userProfileViewModel: UserProfileViewModel)
{
    composable("user_profile_screen")
    {
        User_profile_screen(userProfileViewModel, navController)
    }
}
