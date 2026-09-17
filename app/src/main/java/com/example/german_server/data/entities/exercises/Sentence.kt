package com.example.german_server.data.entities.exercises

data class SentenceExercise(
    val original: String,
    val translation: String?,
    val description: String?,
    val correctWords: List<String>,
    val shuffledWords: List<String>,
    val userWords: List<String> = emptyList(),
    val availableWords: List<String> = emptyList()
)

data class ExerciseSentenceResult(
    val isCorrect: Boolean,
    val userAnswer: String,
    val correctAnswer: String,
    val translation: String? = null,
    val description: String? = null
)