package com.example.german_server.data.entities.exercises

data class AdjectiveDeclensionsExercise(
    val adjectiveId: Long,
    val word: String,
    val question: String,
    val correctForm: String,
    var userAnswer: String? = null      // выбранная кнопка или введённый текст
)

data class ExerciseDeclensionsResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int
)