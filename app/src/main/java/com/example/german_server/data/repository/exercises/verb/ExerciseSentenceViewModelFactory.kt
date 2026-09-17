package com.example.german_server.data.repository.exercises.verb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log
import com.example.german_server.data.repository.exercises.ExerciseSentenceRepository
import com.example.german_server.data.ui.viewModel.exercises.ExercisesSentenceViewModel

class ExerciseSentenceViewModelFactory(
    private val repo: ExerciseSentenceRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("Article_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesSentenceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesSentenceViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
