package com.example.german.data.ui.viewModel.user_profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

import com.example.german.data.AppDatabase
import com.example.german.data.entities.BaseUser

class UserProfileViewModel : ViewModel() {

    private val _currentUser = mutableStateOf<BaseUser?>(null)
    val currentUser: State<BaseUser?> = _currentUser
    init {
        Log.d("VM_LIFECYCLE", "UserProfileViewModel CREATED: $this")
    }

    fun setUser(user: BaseUser) {
        _currentUser.value = user
        Log.d("AUTO_USERMODEL","setUser -> $user in $this")
    }

    fun logout() {
        _currentUser.value = null
    }

    fun isAuthorized(): Boolean {
        return _currentUser.value != null
    }
    fun decreaseLife() {
        currentUser.value?.let { user ->
            val lifes = user.lifes ?: 0
            if (lifes > 0) {
                _currentUser.value = user.copy(lifes = lifes - 1)
            }
        }
    }

    fun addScore(points: Int) {
        currentUser.value?.let { user ->
            val score = user.score ?: 0
            _currentUser.value = user.copy(score = score + points)
        }
    }
}
















