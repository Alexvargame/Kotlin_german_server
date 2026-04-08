package com.example.german_server.data.repository.user_profile

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import java.lang.IllegalArgumentException
import android.content.SharedPreferences

import com.example.german_server.data.dao.BaseUserDao
import com.example.german_server.data.dao.UserAvatarDao
import com.example.german_server.data.ui.components.UpdateQuestsAfterExerciseUseCase

import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel


class UserViewModelFactory(
    private val userDao: BaseUserDao,
    private val avatarDao: UserAvatarDao,
    private val repo: UserProfileRepository,
    private val prefs: SharedPreferences,  // <- ДОБАВЛЯЕМ
    private val updateQuestsAfterExerciseUseCase: UpdateQuestsAfterExerciseUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userDao, avatarDao,repo, prefs, updateQuestsAfterExerciseUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}