package com.example.german_server.data.repository.autorization

import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import androidx.lifecycle.ViewModel

import com.example.german_server.data.AppDatabase
import com.example.german_server.data.ui.viewModel.autorization.AutorizationViewModel



class AutorizationViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AutorizationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AutorizationViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
