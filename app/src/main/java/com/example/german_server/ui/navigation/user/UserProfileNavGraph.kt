package com.example.german_server.ui.navigation.user


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder


import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel

import com.example.german_server.ui.screens.user.User_profile_screen

fun NavGraphBuilder.userProfileNavGraph (
    navController: NavHostController,
    userProfileViewModel: UserViewModel)
{
    composable("user_profile_screen")
    {
        User_profile_screen(userProfileViewModel, navController)
    }
}
