package com.example.german_server.data.entities.exercises



data class WordsPairExercise(
    val wordId: Long,               // уникальный id слова
    val german: String,              // слово на немецком
    val russian: String,             // перевод
    val variantsAnswer: List<String>, // 3 варианта перевода (1 правильный + 2 случайных)
    var selectedOption: String? = null // Выбранный вариант
)

data class ExerciseWordsPairResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int
)