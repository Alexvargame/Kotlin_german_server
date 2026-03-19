package com.example.german_server.ui.navigation.support_chat


import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.NavGraphBuilder
import com.example.german_server.data.ui.viewModel.support_chat.SupportChatViewModel


import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel

import com.example.german_server.ui.screens.support_chat.Support_chat_message_send_screen

fun NavGraphBuilder.supportChatMessageSendNavGraph (
    navController: NavHostController,
    supportChatViewModel: SupportChatViewModel,
    userProfileViewModel: UserViewModel)
{
    composable("support_chat_message_send_screen")
    {
        Support_chat_message_send_screen(userProfileViewModel, supportChatViewModel,navController)
    }
}
