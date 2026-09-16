package com.example.german_server.data.entities.exercises



data class VerbPrepositionExercise(
   // val id: Int,
    val word: String,
    val preposition: String?,
    val variantsAnswer: List<String?>,
    var selectedOption: String? = null // Выбранный вариант
)
data class VerbPrepositionAnswerDetail(
    val word: String,
    val correctAnswer: String,   // "für/Akk"
    val userAnswer: String?      // "mit/Dat" или null
)
data class ExerciseVerbPrepositionResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int,
    val details: List<VerbPrepositionAnswerDetail>

)