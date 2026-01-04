package com.example.german.test_add

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Context

import com.example.german.data.AppDatabase
import com.example.german.data.repository.exercises.adjective.ExerciseAdjectiveCasusRepository


class TestAdjectiveRepository(private val context: Context) {

    fun testAdjectives() {
        val db = AppDatabase.getInstance(context)
        val nounDao = db.nounDao()
        val nounDeclensions = db.nounDeclensionsFormDao()
        val adjectiveDao = db.adjectiveDao()
        val articleDao = db.articleDao()
        val repo = ExerciseAdjectiveCasusRepository(nounDeclensions, adjectiveDao,
            articleDao, nounDao)


        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_ADJ", "Fetching random adjective forms...")

            val adjectives = repo.getRandomAdjectiveForm(1) // получаем 1 случайное прилагательное
            adjectives.forEach { adj ->
                Log.d("TEST_ADJ", "ID: ${adj.id}")
                Log.d("TEST_ADJ", "Options: ${adj.adjectiveOptions.joinToString(", ")}")
            }

            val nouns = repo.getRandomNounDeclensionsForm(1) // 1 случайное существительное
            nouns.forEach { noun ->
                Log.d("TEST_ADJ", "Noun ID: ${noun.id}")
                Log.d("TEST_ADJ", "Noun forms: ${noun.nounOptions.joinToString(", ")}")
            }
            val nounForm = nouns.first()
            val adjectiveForm = adjectives.first()
            // СЮДА ДОБАВВИТЬ КОД для проверки функции
            val exercise = repo.generateExercises(nounForm, adjectiveForm)
            Log.d("TEST_ADJ", "Question: ${exercise.question}")
            Log.d("TEST_ADJ", "Article options: ${exercise.articleOptions.joinToString(", ")}")
            Log.d("TEST_ADJ", "Adjective options: ${exercise.adjectiveOptions.joinToString(", ")}")
            Log.d("TEST_ADJ", "Noun options: ${exercise.nounOptions.joinToString(", ")}")
            Log.d("TEST_ADJ", "Correct Article: ${exercise.correctArticle}")
            Log.d("TEST_ADJ", "Correct Adjective Form: ${exercise.correctAdjectiveForm}")
            Log.d("TEST_ADJ", "Correct Noun Form: ${exercise.correctNounForm}")
        }
    }
}