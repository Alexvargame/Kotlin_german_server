package com.example.german.data.repository.exercises.adjective

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log
import com.example.german.data.repository.exercises.adjective.ExerciseAdjectiveCasusRepository

import com.example.german.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveCasusViewModel

class ExerciseAdjectiveCasusViewModelFactory(
    private val repo: ExerciseAdjectiveCasusRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("Article_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesAdjectiveCasusViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesAdjectiveCasusViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
