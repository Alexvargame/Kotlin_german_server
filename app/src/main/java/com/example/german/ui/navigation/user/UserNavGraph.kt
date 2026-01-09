package com.example.german.ui.navigation.user


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder


import com.example.german.data.ui.viewModel.user_profile.UserViewModel

import com.example.german.ui.screens.user.User_screen

fun NavGraphBuilder.userNavGraph (
    navController: NavHostController,
    userProfileViewModel: UserViewModel)
{
    composable("user_screen")
    {
        User_screen(userProfileViewModel, navController)
    }
}
