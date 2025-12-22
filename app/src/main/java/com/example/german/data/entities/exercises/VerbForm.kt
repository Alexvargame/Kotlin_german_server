package com.example.german.data.entities.exercises

data class VerbFormExercise(
    val verbId: Long,
    val infinitive: String,
    val pronoun: String,
    val correctForm: String,  // форма для выбранного времени и местоимения
    val variants: List<String>? = null, // для кнопок
    var userAnswer: String? = null      // выбранная кнопка или введённый текст
)

data class ExerciseVerbFormResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int
)