package com.example.german_server.data.entities.exercises

data class AnswerDetailUi(
    val leftText: String,          // "gehen (ich)" / "danken" / "schön (Komparativ)"
    val userAnswer: String?,       // "gehst"
    val correctAnswer: String,     // "gehe"
    val isCorrect: Boolean         // вычислено снаружи
)