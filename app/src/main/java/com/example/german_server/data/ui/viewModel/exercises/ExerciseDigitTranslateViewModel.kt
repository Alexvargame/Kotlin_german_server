package com.example.german_server.data.ui.viewModel.exercises


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import com.example.german_server.data.entities.exercises.ExerciseResult
import com.example.german_server.data.entities.exercises.DigitTranslateExercise
import com.example.german_server.data.repository.exercises.ExerciseDigitTranslateRepository

class ExercisesDigitTranslateViewModel(
    private val repo: ExerciseDigitTranslateRepository
) : ViewModel() {

    var exercises by mutableStateOf<List<DigitTranslateExercise>>(emptyList())
        private set

    fun loadExercises() {
        val digits = repo.getRandomNumbers()
        exercises = repo.generateExercises(digits)
    }
    fun selectAnswer(index: Int, answer: Int) {
        exercises = exercises.toMutableList().also {
            it[index] = it[index].copy(selectedOption = answer)
        }
    }
    fun checkAnswers(): ExerciseResult{
        val correctCount = exercises.count { it.selectedOption == it.digit }
        val wrongCount = exercises.count { it.selectedOption != null && it.selectedOption != it.digit }
        return ExerciseResult(correctCount, wrongCount, exercises.size)
    }
}
