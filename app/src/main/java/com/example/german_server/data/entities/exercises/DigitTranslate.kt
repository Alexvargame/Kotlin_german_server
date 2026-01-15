package com.example.german_server.data.entities.exercises



data class DigitTranslateExercise(
   // val id: Int,
    val digit: Int,
    val germanTranslate: String,
    val variantsAnswer: List<Int>,       // 3 варианта ответа (1 правильный + 2 случайных)
    var selectedOption: Int? = null // Выбранный вариант
)

data class ExerciseResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int
)