package com.example.german_server.ui.navigation

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder


import com.example.german_server.data.AppDatabase
import com.example.german_server.data.repository.registraiton.RegistrationViewModelFactory
import com.example.german_server.data.repository.registraiton.UserRegistrationRepository
import com.example.german_server.data.ui.viewModel.autorization.AutorizationViewModel
import com.example.german_server.data.repository.autorization.AutorizationViewModelFactory

import com.example.german_server.data.ui.viewModel.registration.RegistrationViewModel
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.ui.screens.Registration_screen
import com.example.german_server.ui.screens.Start_app_screen


fun NavGraphBuilder.authRegNavGraph(
    navController: NavHostController,
    userProfileViewModel: UserViewModel)
{
    composable("start_app"){
        val context = LocalContext.current
        val db = AppDatabase.getInstance(context)
        val factory = AutorizationViewModelFactory(db)
        val autoviewModel: AutorizationViewModel =
            viewModel(factory = factory)
        Start_app_screen(userProfileViewModel, autoviewModel, navController) }
    composable("registration") {
        Log.d("ER_NAV_DEBUG", "Открыт экран регистрации")
        val context = LocalContext.current.applicationContext
        val repo =
            UserRegistrationRepository(AppDatabase.getInstance(context).registrationDao())
        val factory = RegistrationViewModelFactory(repo)

        val registrationViewModel: RegistrationViewModel =
            viewModel(factory = factory)

        Log.d("NAV_DEBUG", "Открыт экран регистрации")
        Registration_screen(userProfileViewModel, registrationViewModel, navController)
    }
}