package com.example.german_server.data.repository.user_profile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import com.example.german_server.data.AppDatabase
import java.lang.IllegalArgumentException


import com.example.german_server.data.ui.viewModel.user_profile.UserProfileViewModel

class UserProfileViewModelFactory(
    private val db: AppDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
