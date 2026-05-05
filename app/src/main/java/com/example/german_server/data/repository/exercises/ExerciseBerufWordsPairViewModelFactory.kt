package com.example.german_server.data.repository.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log

import com.example.german_server.data.ui.viewModel.exercises.ExercisesBerufWordsPairViewModel

class ExerciseBerufWordsPairViewModelFactory(
    private val repo: ExerciseBerufWordsPairRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("WORD_PAIR", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesBerufWordsPairViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesBerufWordsPairViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
