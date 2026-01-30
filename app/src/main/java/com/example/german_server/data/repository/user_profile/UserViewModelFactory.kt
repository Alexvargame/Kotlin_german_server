package com.example.german_server.data.repository.user_profile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import java.lang.IllegalArgumentException

import com.example.german_server.data.dao.BaseUserDao

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
class UserViewModelFactory(
    private val userDao: BaseUserDao,
    private val repo: UserProfileRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userDao, repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
