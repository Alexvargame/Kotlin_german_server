package com.example.german.data.ui.viewModel.exercises.adjective


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import android.util.Log


import com.example.german.data.entities.exercises.ExerciseAdjectiveKomparativSuperlativResult
import com.example.german.data.entities.exercises.AdjectiveKomparativSuperlativExercise
import com.example.german.data.repository.exercises.adjective.ExerciseAdjectiveKomparativSuperlativRepotory

class ExercisesAdjectiveKomparativSuperlativViewModel(
    private val repo: ExerciseAdjectiveKomparativSuperlativRepotory
) : ViewModel() {

    var exercises by mutableStateOf<List<AdjectiveKomparativSuperlativExercise>>(emptyList())
        private set

    fun loadExercises() {
        viewModelScope.launch {
            val adjs = repo.getRandomAdjectives(5) //
            adjs.forEach {
                Log.d("Adj_forms_", "WordId: ${it.id}, Word: ${it.word}, ")
            }
            exercises = repo.generateExercises(adjs)
        }
    }
    fun selectAnswer(index: Int, answer: Long) {
        //exercises = exercises.toMutableList().also {
          //  it[index] = it[index].copy(selectedOption = answer)
       // }
    }
    fun checkAnswers(): ExerciseAdjectiveKomparativSuperlativResult {
        val correctCount = exercises.count { ex ->
            val userAnswer = ex.userAnswer?.trim()?.lowercase()
            val correct = ex.correctForm.trim().lowercase()
            userAnswer == correct
        }

        val wrongCount = exercises.count { ex ->
            val userAnswer = ex.userAnswer?.trim()?.lowercase()
            val correct = ex.correctForm.trim().lowercase()
            userAnswer != null && userAnswer != correct
        }

        return ExerciseAdjectiveKomparativSuperlativResult(
            correctCount = correctCount,
            wrongCount = wrongCount,
            totalQuestions = exercises.size
        )
    }

}
