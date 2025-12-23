package com.example.german.data.ui.viewModel.exercises.pronoun


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import android.util.Log


import com.example.german.data.entities.exercises.ExercisePronounResult
import com.example.german.data.entities.exercises.PronounExercise
import com.example.german.data.repository.exercises.pronoun.ExercisePronounButtonRepoRepository

class ExercisesPronounButtonViewModel(
    private val repo: ExercisePronounButtonRepoRepository
) : ViewModel() {

    var exercises by mutableStateOf<List<PronounExercise>>(emptyList())
        private set

    fun loadExercises() {
        viewModelScope.launch {
            val pronouns = repo.getRandomPronouns(5) //
            pronouns.forEach {
                Log.d("Pronoun_button_", "WordId: ${it.id}, Word: ${it.word}, ")
            }
            exercises = repo.generateExercises(pronouns)
        }
    }
    fun selectAnswer(index: Int, answer: String) {
        exercises = exercises.toMutableList().also {
            it[index] = it[index].copy(userAnswer = answer)
        }
    }
    fun checkAnswers(): ExercisePronounResult {
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

        return ExercisePronounResult(
            correctCount = correctCount,
            wrongCount = wrongCount,
            totalQuestions = exercises.size
        )
    }

}
