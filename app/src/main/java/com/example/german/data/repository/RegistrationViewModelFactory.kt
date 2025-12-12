package com.example.german.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


import com.example.german.data.ui.registration.RegistrationViewModel

class RegistrationViewModelFactory(
    private val repo: UserRegistrationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistrationViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}