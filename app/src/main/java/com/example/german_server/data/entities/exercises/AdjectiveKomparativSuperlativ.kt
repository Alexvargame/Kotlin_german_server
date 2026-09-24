package com.example.german_server.data.entities.exercises

data class AdjectiveKomparativSuperlativExercise(
    val adjectiveId: Long,
    val word: String,
    val question: String,
    val correctForm: String,
    var userAnswer: String? = null      // выбранная кнопка или введённый текст
)

data class AdjectiveKomparativSuperlativAnswerDetail(
    val word: String,
    val question: String,
    val correctAnswer: String,
    var userAnswer: String?,
)
data class ExerciseAdjectiveKomparativSuperlativResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int,
    val details: List<AdjectiveKomparativSuperlativAnswerDetail>
)