package com.example.german_server.data.ui.viewModel.exercises.adjective


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import android.util.Log
import com.example.german_server.data.entities.exercises.AdjectiveDeclensionsAnswerDetail


import com.example.german_server.data.entities.exercises.ExerciseDeclensionsResult
import com.example.german_server.data.entities.exercises.AdjectiveDeclensionsExercise
import com.example.german_server.data.entities.exercises.ExerciseArticleResult
import com.example.german_server.data.repository.exercises.adjective.ExerciseDeclensionsRepository

class ExercisesAdjectiveDeclensionsViewModel(
    private val repo: ExerciseDeclensionsRepository
) : ViewModel() {

    var exercises by mutableStateOf<List<AdjectiveDeclensionsExercise>>(emptyList())
        private set
    var lastResult: ExerciseDeclensionsResult? = null
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
    fun checkAnswers(): ExerciseDeclensionsResult {

        val details = exercises.map { ex ->
            AdjectiveDeclensionsAnswerDetail(
                word = ex.word,
                question = ex.question,
                correctAnswer = ex.correctForm,
                userAnswer = ex.userAnswer
            )
        }
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

        val result = ExerciseDeclensionsResult(
            correctCount = correctCount,
            wrongCount = wrongCount,
            totalQuestions = exercises.size,
            details = details
        )
        lastResult = result
        return result

    }

}
