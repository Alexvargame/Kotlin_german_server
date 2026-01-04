package com.example.german.data.repository.exercises.adjective

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log
import com.example.german.data.repository.exercises.adjective.ExerciseDeclensionsRepository
import com.example.german.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveDeclensionsViewModel

import com.example.german.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveKomparativSuperlativViewModel

class ExerciseAdjectiveDeclensionsViewModelFactory(
    private val repo: ExerciseDeclensionsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("Article_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesAdjectiveDeclensionsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesAdjectiveDeclensionsViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
