package com.example.german.data.repository.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log

import com.example.german.data.ui.viewModel.exercises.ExercisesVerbPerfectViewModel

class ExerciseVerbPerfectViewModelFactory(
    private val repo: ExerciseVerbPerfectFormRepoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("Verb_forms_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesVerbPerfectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesVerbPerfectViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
