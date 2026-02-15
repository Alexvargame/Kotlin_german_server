package com.example.german_server.ui.navigation


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder


import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel

import com.example.german_server.ui.screens.Rating_screen

fun NavGraphBuilder.ratingNavGraph (
    navController: NavHostController,
    userProfileViewModel: UserViewModel)
{
    composable("rating_screen")
    {
        Rating_screen(navController, userProfileViewModel )
    }
}
