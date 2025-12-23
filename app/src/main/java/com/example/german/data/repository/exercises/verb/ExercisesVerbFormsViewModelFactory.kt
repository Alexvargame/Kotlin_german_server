package com.example.german.data.repository.exercises.verb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

import com.example.german.data.AppDatabase
import com.example.german.data.ui.viewModel.exercises.verb.ExercisesVerbFormsViewModel

class ExercisesVerbFormsViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExercisesVerbFormsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesVerbFormsViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}