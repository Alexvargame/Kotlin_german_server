package com.example.german_server.data.repository.exercises.pronoun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log

import com.example.german_server.data.ui.viewModel.exercises.pronoun.ExercisesPronounButtonViewModel

class ExercisePronounButtonViewModelFactory(
    private val repo: ExercisePronounButtonRepoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("Pronuns_enter_forms_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesPronounButtonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesPronounButtonViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
