package com.example.german_server.ui.navigation

import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.ui.viewModel.autorization.AutorizationViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel


import com.example.german_server.ui.screens.BlockScreen

fun NavGraphBuilder.blockNavGraph (
    navController: NavHostController,
    userProfileViewModel: UserViewModel,
    autoviewModel: AutorizationViewModel,
    )
{
    composable("block_screen")
    {
        BlockScreen(userProfileViewModel,autoviewModel, navController)
    }
}
