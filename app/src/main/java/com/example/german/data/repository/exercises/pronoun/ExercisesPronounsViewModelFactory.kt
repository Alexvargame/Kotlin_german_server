package com.example.german.data.repository.exercises.pronoun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

import com.example.german.data.AppDatabase
import com.example.german.data.ui.viewModel.exercises.pronoun.ExercisesPronounsViewModel

class ExercisesPronounsViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExercisesPronounsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesPronounsViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}