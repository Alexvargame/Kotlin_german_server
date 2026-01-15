package com.example.german_server.data.repository.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log

import com.example.german_server.data.ui.viewModel.exercises.ExercisesArticleViewModel

class ExerciseArticleViewModelFactory(
    private val repo: ExerciseArticleRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("Article_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesArticleViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
