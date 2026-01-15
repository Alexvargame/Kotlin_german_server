package com.example.german_server.data.repository.exercises.adjective

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import android.util.Log
import com.example.german_server.data.repository.exercises.adjective.ExerciseAdjectiveKomparativSuperlativRepotory

import com.example.german_server.data.ui.viewModel.exercises.adjective.ExercisesAdjectiveKomparativSuperlativViewModel

class ExerciseAdjectiveKomparativSuperlativViewModelFactory(
    private val repo: ExerciseAdjectiveKomparativSuperlativRepotory
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.e("Article_", "FACtory")
        if (modelClass.isAssignableFrom(ExercisesAdjectiveKomparativSuperlativViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExercisesAdjectiveKomparativSuperlativViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
