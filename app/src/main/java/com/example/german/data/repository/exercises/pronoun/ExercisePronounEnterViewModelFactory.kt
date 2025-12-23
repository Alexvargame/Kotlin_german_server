package com.example.german.data.repository.exercises.pronoun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log

import com.example.german.data.ui.viewModel.exercises.pronoun.ExercisesPronounEnterViewModel

class ExercisePronounEnterViewModelFactory(
    private val repo: ExercisePronounEnterRepoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("Pronuns_enter_forms_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesPronounEnterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesPronounEnterViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
