package com.example.german.data.repository.user_profile

import androidx.lifecycle.ViewModelProvider

import com.example.german.data.AppDatabase

class UserProfileViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
  /*  override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserProfileViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }*/
}
