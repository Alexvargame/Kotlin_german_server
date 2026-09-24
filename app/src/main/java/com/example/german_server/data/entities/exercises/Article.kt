package com.example.german_server.data.entities.exercises


import com.example.german_server.data.entities.Article




data class ArticleExercise(
   // val id: Int,
    val word: String,
    val article: Long,
    val variantsAnswer: List<Article>,
    var selectedOption: Long? = null // Выбранный вариант
)

data class ArticleAnswerDetail(
    val word: String,
    val correctAnswer: String,   // правильный: "der"
    var userAnswer: String?, // что выбрал: "die"
)
data class ExerciseArticleResult(
    val correctCount: Int,
    val wrongCount: Int,
    val totalQuestions: Int,
    val details: List<ArticleAnswerDetail>
)
//data class ExerciseArticleResult(
//    val correctCount: Int,
//    val wrongCount: Int,
//    val totalQuestions: Int
//)