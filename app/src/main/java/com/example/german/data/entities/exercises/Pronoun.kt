package com.example.german.data.entities.exercises

data class PronounExercise(
    val verbId: Long,
    val word: String,
    val casus: String,
    val correctForm: String,  // форма для выбранного времени и местоимения
    val variants: List<String>? = null, // для кнопок
    var userAnswer: String? = null      // выбранная кнопка или введённый текст
)

data class ExercisePronounResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int
)