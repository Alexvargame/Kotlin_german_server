package com.example.german_server.ui.navigation


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder



import com.example.german_server.ui.screens.BackUpscreen

fun NavGraphBuilder.backUpNavGraph (
    navController: NavHostController, )
{
    composable("back_up_screen")
    {
        BackUpscreen( navController)
    }
}
