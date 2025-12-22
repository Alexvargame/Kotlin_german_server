package com.example.german.data.repository.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log

import com.example.german.data.ui.viewModel.exercises.ExercisesWordsPairViewModel

class ExerciseWordsPairViewModelFactory(
    private val repo: ExerciseWordsPairRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("WORD_PAIR", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesWordsPairViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesWordsPairViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
