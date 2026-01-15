package com.example.german_server.data.entities.exercises




data class ExerciseAdjectiveCasus(
    val noun: Long,
    val adjective: Long,

    val gender: String,
    val case: String,

    // варианты для кнопок
    val articleOptions: List<String>,
    val adjectiveOptions: List<String>,
    val nounOptions: List<String>,

    val question: String,
    // правильные формы
    val correctArticle: String,
    val correctAdjectiveForm: String,
    val correctNounForm: String,

    var selectedArticle: String? = null,
    var selectedAdjective: String? = null,
    var selectedNoun: String? = null,

    )


data class ExerciseAdjectiveCasusResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int
)