package com.example.german.data.entities.exercises


import com.example.german.data.entities.Article




data class ArticleExercise(
   // val id: Int,
    val word: String,
    val article: Long,
    val variantsAnswer: List<Article>,
    var selectedOption: Long? = null // Выбранный вариант
)

data class ExerciseArticleResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int
)