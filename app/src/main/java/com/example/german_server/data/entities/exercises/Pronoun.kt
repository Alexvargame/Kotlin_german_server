package com.example.german_server.data.entities.exercises

data class PronounExercise(
    val verbId: Long,
    val word: String,
    val casus: String,
    val correctForm: String,  // форма для выбранного времени и местоимения
    val variants: List<String>? = null, // для кнопок
    var userAnswer: String? = null      // выбранная кнопка или введённый текст
)
data class PronounAnswerDetail(
    val word: String,
    val casus: String,
    val correctAnswer: String,   // правильный: "der"
    var userAnswer: String?, // что выбрал: "die"
)
data class ExercisePronounResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int,
    val details : List<PronounAnswerDetail>
)