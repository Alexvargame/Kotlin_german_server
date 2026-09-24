package com.example.german_server.data.ui.viewModel.exercises


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import android.util.Log
import com.example.german_server.data.entities.exercises.ArticleAnswerDetail


import com.example.german_server.data.entities.exercises.ExerciseArticleResult
import com.example.german_server.data.entities.exercises.ArticleExercise
import com.example.german_server.data.entities.exercises.ExerciseVerbPrepositionResult
import com.example.german_server.data.entities.exercises.VerbPrepositionAnswerDetail
import com.example.german_server.data.repository.exercises.ExerciseArticleRepository

class ExercisesArticleViewModel(
    private val repo: ExerciseArticleRepository
) : ViewModel() {

    var exercises by mutableStateOf<List<ArticleExercise>>(emptyList())
        private set

    var lastResult: ExerciseArticleResult? = null
        private set

    fun loadExercises() {
        viewModelScope.launch {
            val nouns = repo.getRandomNouns(5) //
            nouns.forEach {
                Log.d("ARTICLE__", "WordId: ${it.id}, Word: ${it.word}, ArticleId: ${it.article}")
            }
            exercises = repo.generateExercises(nouns)
        }
    }
    fun selectAnswer(index: Int, answer: Long) {
        exercises = exercises.toMutableList().also {
            it[index] = it[index].copy(selectedOption = answer)
        }
    }
    fun checkAnswers(): ExerciseArticleResult{
        val details = exercises.map { ex ->
            val correct = ex.variantsAnswer.find { it.id == ex.article }?.name ?: ""
            val user = ex.selectedOption?.let { id ->
                ex.variantsAnswer.find { it.id == id }?.name
            } ?: ""
            ArticleAnswerDetail(
                word = ex.word,
                correctAnswer = correct,
                userAnswer = user
            )
        }
        val correctCount = exercises.count { it.selectedOption == it.article }
        val wrongCount = exercises.count { it.selectedOption != null && it.selectedOption != it.article }
        val result =  ExerciseArticleResult(correctCount, wrongCount, exercises.size, details)
        lastResult = result
        return result
    }
}
