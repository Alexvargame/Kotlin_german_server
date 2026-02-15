package com.example.german_server.ui.navigation.user


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder


import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel

import com.example.german_server.ui.screens.user.Another_profile_screen

fun NavGraphBuilder.anotherProfileNavGraph (
    navController: NavHostController,
    userProfileViewModel: UserViewModel)
{
    composable("another_profile_screen")
    {
        Another_profile_screen(userProfileViewModel, navController)
    }
}
