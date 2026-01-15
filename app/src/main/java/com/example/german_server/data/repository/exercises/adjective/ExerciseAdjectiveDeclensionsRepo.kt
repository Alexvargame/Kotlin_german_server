package com.example.german_server.data.repository.exercises.adjective

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.german_server.data.dao.AdjectiveDao
import com.example.german_server.data.entities.Adjective
import com.example.german_server.data.entities.exercises.AdjectiveDeclensionsExercise

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

typealias Declensions =
        Map<String, Map<String, Map<String, Map<String, String>>>>

fun String.toDeclensions(): Declensions? {
    val type = object : TypeToken<Declensions>() {}.type
    return Gson().fromJson<Declensions>(this, type)
}
class ExerciseDeclensionsRepository(
    private val adjectiveDao: AdjectiveDao,
) {

    suspend fun getRandomAdjectives(count: Int = 5):
            List<WordWithDeclensions> = withContext(Dispatchers.IO) {
        Log.e("Adj_forms_", "Repo")
        val adjs = mutableListOf<WordWithDeclensions>()
        Log.e("Adj_forms_", "allwords")
        adjs += adjectiveDao.getRandomAdjectives(count).map {
            WordWithDeclensions(
                it.wordPtrId, it.word,
                it
            )
        }
        adjs.shuffled().take(count)
    }

    fun generateExercises(adjs: List<WordWithDeclensions>):
            List<AdjectiveDeclensionsExercise> {

        //var adj_form by mutableStateOf<String?>(null)
        return adjs.mapIndexed { index, adj ->
            val declensionsMap = adj.obj.declensions?.toDeclensions()
            val case = casesChoice.random()
            val decl = declensions.random()
            val entry = genders[case]
                ?.entries
                ?.randomOrNull()

            val gender_name = entry?.key
            val article = entry?.value

            val question = "${adj.word} в Склонение: ${decl}, Падеж: ${case}, Род: ${gender_name}, Артикль: ${article}?"
            val adj_form = declensionsMap?.get(decl)
                ?.get(case)
                ?.get(gender_name)
                ?.get(article)

            AdjectiveDeclensionsExercise(
                adjectiveId = adj.id,
                word = adj.word,
                question = question,
                correctForm = adj_form ?: "",
                userAnswer = null
            )
        }
    }
}


data class WordWithDeclensions(
   val id: Long,
   val word: String,
   val obj: Adjective
)

val genders: Map<String, Map<String, String>> = mapOf(
    "Nominativ" to mapOf(
        "Maskulinum" to "der",
        "Femininum" to "die",
        "Neutrum" to "das",
        "Plural" to "die"
    ),
    "Akkusativ" to mapOf(
        "Maskulinum" to "den",
        "Femininum" to "die",
        "Neutrum" to "das",
        "Plural" to "die"
    ),
    "Dativ" to mapOf(
        "Maskulinum" to "dem",
        "Femininum" to "der",
        "Neutrum" to "dem",
        "Plural" to "den"
    ),
    "Genitiv" to mapOf(
        "Maskulinum" to "des",
        "Femininum" to "der",
        "Neutrum" to "des",
        "Plural" to "der"
    )
)
val casesChoice = listOf(
    "Nominativ",
    "Akkusativ",
    "Dativ",
    "Genitiv"
)
val declensions = listOf("stark")
// позже добавишь:
// val declensions = listOf("stark", "schwach", "gemischt")
