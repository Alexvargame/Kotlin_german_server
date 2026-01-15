package com.example.german_server.data.repository.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log

import com.example.german_server.data.ui.viewModel.exercises.ExercisesDigitTranslateViewModel

class ExerciseDigitTranslateViewModelFactory(
    private val repo: ExerciseDigitTranslateRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("DIGIT_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesDigitTranslateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesDigitTranslateViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
