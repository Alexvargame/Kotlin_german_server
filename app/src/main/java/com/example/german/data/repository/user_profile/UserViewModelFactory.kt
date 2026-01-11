package com.example.german.data.repository.user_profile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import java.lang.IllegalArgumentException

import com.example.german.data.dao.BaseUserDao

import com.example.german.data.ui.viewModel.user_profile.UserViewModel

/*class UserProfileViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
   override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserProfileViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/
class UserViewModelFactory(
    private val userDao: BaseUserDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
