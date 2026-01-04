package com.example.german.data.entities.exercises

data class AdjectiveKomparativSuperlativExercise(
    val adjectiveId: Long,
    val word: String,
    val question: String,
    val correctForm: String,
    var userAnswer: String? = null      // выбранная кнопка или введённый текст
)

data class ExerciseAdjectiveKomparativSuperlativResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int
)