package com.example.german_server.data.repository.support_chat

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.example.german_server.data.repository.support_chat.SupportChatMessageViewModelFactory
import java.lang.IllegalArgumentException


import com.example.german_server.data.ui.viewModel.support_chat.SupportChatViewModel

class SupportChatMessageViewModelFactory(
    private val repo: SupportChatMessageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SupportChatViewModel::class.java)) {
            return SupportChatViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
