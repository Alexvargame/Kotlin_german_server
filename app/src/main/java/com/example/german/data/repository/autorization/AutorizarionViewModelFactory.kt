package com.example.german.data.repository.autorization

import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import androidx.lifecycle.ViewModel

import com.example.german.data.AppDatabase
import com.example.german.data.ui.viewModel.autorization.AutorizationViewModel



class AutorizationViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AutorizationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AutorizationViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
