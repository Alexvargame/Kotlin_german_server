package com.example.german.ui.navigation.user


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder


import com.example.german.data.ui.viewModel.user_profile.UserViewModel

import com.example.german.ui.screens.user.User_profile_edit_screen

fun NavGraphBuilder.userProfileEditNavGraph (
    navController: NavHostController,
    userProfileViewModel: UserViewModel)
{
    composable("user_profile_edit_screen")
    {
        User_profile_edit_screen(userProfileViewModel, navController)
    }
}
