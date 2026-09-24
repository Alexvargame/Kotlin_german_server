package com.example.german_server.data.entities.exercises

data class VerbFormExercise(
    val verbId: Long,
    val infinitive: String,
    val pronoun: String,
    val correctForm: String,  // форма для выбранного времени и местоимения
    val variants: List<String>? = null, // для кнопок
    var userAnswer: String? = null      // выбранная кнопка или введённый текст
)
data class VerbFormAnswerDetail(
    val verb: String,           // "gehen"
    val pronoun: String,        // "ich"
    val correctAnswer: String,  // "gehe"
    val userAnswer: String?     // "gehst" или null
)

data class ExerciseVerbFormResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int,
    val details: List<VerbFormAnswerDetail>  // ← добавить
)

//data class ExerciseVerbFormResult(
//    val correctCount: Int,
//    val wrongCount: Int,
//    val totalQuestions: Int
//)