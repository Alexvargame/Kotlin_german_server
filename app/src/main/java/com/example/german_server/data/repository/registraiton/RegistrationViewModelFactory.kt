package com.example.german_server.data.repository.registraiton

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.german_server.data.ui.viewModel.registration.RegistrationViewModel
import java.lang.IllegalArgumentException

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