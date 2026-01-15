package com.example.german_server.data.repository.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

import com.example.german_server.data.AppDatabase
import com.example.german_server.data.ui.viewModel.exercises.ExercisesViewModel

class ExercisesViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExercisesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}