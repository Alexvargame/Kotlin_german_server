package com.example.german_server.data.repository.exercises.verb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log
import com.example.german_server.data.repository.exercises.verb.ExerciseVerbPrepositionRepository

import com.example.german_server.data.ui.viewModel.exercises.verb.ExercisesVerbPrepositionViewModel

class ExerciseVerbPrepositionViewModelFactory(
    private val repo: ExerciseVerbPrepositionRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("Article_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesVerbPrepositionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesVerbPrepositionViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
