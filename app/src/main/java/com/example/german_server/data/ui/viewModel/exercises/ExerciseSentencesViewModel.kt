package com.example.german_server.data.ui.viewModel.exercises


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import android.util.Log


import com.example.german_server.data.entities.exercises.ExerciseSentenceResult
import com.example.german_server.data.entities.exercises.SentenceExercise
import com.example.german_server.data.repository.exercises.ExerciseSentenceRepository

class ExercisesSentenceViewModel(
    private val repo: ExerciseSentenceRepository
) : ViewModel() {

    var exercise by mutableStateOf<SentenceExercise?>(null)
        private set

    fun loadExercise() {
        viewModelScope.launch {
            val sentence = repo.getRandomSentences(1)
            sentence.firstOrNull()?.let {
                Log.d("SENTENCE_", "id=${it.id}, sentence=${it.sentence}")
                exercise = repo.generateExercise(it)
            }
        }
    }
    fun addWord(word: String) {
        val ex = exercise ?: return
        exercise = ex.copy(
            userWords = ex.userWords + word,
            availableWords = ex.availableWords - word
        )
    }

    fun removeWord(word: String) {
        val ex = exercise ?: return
        exercise = ex.copy(
            userWords = ex.userWords - word,
            availableWords = ex.availableWords + word
        )
    }

    fun checkAnswer(): ExerciseSentenceResult {
        val ex = exercise ?: return ExerciseSentenceResult(false, "", "")
        val userAnswer = ex.userWords.joinToString(" ")
        val correctAnswer = ex.correctWords.joinToString(" ")
        return ExerciseSentenceResult(
            isCorrect = userAnswer == correctAnswer,
            userAnswer = userAnswer,
            correctAnswer = correctAnswer ?: correctAnswer,
            translation = ex.translation,
            description = ex.description

        )
    }
}
