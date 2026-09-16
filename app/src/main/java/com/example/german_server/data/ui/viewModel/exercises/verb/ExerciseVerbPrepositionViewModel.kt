package com.example.german_server.data.ui.viewModel.exercises.verb


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import android.util.Log


import com.example.german_server.data.entities.exercises.ExerciseVerbPrepositionResult
import com.example.german_server.data.entities.exercises.VerbPrepositionAnswerDetail
import com.example.german_server.data.entities.exercises.VerbPrepositionExercise
import com.example.german_server.data.repository.exercises.verb.ExerciseVerbPrepositionRepository

class ExercisesVerbPrepositionViewModel(
    private val repo: ExerciseVerbPrepositionRepository
) : ViewModel() {

    var exercises by mutableStateOf<List<VerbPrepositionExercise>>(emptyList())
        private set
    var lastResult: ExerciseVerbPrepositionResult? = null
        private set

    fun loadExercises() {
        viewModelScope.launch {
            val verbs = repo.getRandomVerbs(5) //
            verbs.forEach {
                Log.d("ARTICLE__", "WordId: ${it.id}, Word: ${it.word}, Prep[pos: ${it.preposition}")
            }
            exercises = repo.generateExercises(verbs)
        }
    }
    fun selectAnswer(index: Int, answer: String?) {
        exercises = exercises.toMutableList().also {
            it[index] = it[index].copy(selectedOption = answer)
        }
    }
    fun checkAnswers(): ExerciseVerbPrepositionResult{
        val details = exercises.map { ex ->
            VerbPrepositionAnswerDetail(
                word = ex.word,
                correctAnswer = ex.preposition ?: "",
                userAnswer = ex.selectedOption
            )
        }
        val correctCount = exercises.count { it.selectedOption == it.preposition }
        val wrongCount = exercises.count { it.selectedOption != null && it.selectedOption != it.preposition }

        val result =  ExerciseVerbPrepositionResult(correctCount, wrongCount, exercises.size, details)
        lastResult = result
        return result
    }
}
