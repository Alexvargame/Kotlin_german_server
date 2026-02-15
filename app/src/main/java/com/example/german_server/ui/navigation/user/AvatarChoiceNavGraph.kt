package com.example.german_server.ui.navigation.user


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder


import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel

import com.example.german_server.ui.screens.user.AvatarChoiceScreen
fun NavGraphBuilder.avatarChoiceNavGraph (
    navController: NavHostController,
    userProfileViewModel: UserViewModel)
{
    composable("avatar_choice_screen")
    {
        AvatarChoiceScreen(userProfileViewModel, navController)
    }
}
