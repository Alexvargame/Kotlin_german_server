package com.example.german_server.data.repository.exercises.verb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log

import com.example.german_server.data.ui.viewModel.exercises.verb.ExercisesVerbPresentViewModel

class ExerciseVerbPresentViewModelFactory(
    private val repo: ExerciseVerbPresentFormRepoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("Verb_forms_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesVerbPresentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesVerbPresentViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
