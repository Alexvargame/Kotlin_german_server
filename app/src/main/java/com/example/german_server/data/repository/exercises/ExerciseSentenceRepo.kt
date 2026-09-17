package com.example.german_server.data.repository.exercises

import android.util.Log
import com.example.german_server.data.dao.NounDao
import com.example.german_server.data.dao.SentenceDao
import com.example.german_server.data.entities.SentenceEntity
import com.example.german_server.data.entities.exercises.SentenceExercise

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.String

class ExerciseSentenceRepository(
    private val sentenceDao: SentenceDao,
) {

    suspend fun getRandomSentences(count: Int = 1):
            List<Sentence> = withContext(Dispatchers.IO) {
        Log.e("WORD_PAIR_", "Repo")
        val sentences = mutableListOf<Sentence>()
        Log.e("WORD_PAIR_", "allwords")
        sentences += sentenceDao.getRandomSentences(count).map {
            Sentence(it.id, it.sentence,  it.translation, it.description)
        }
        sentences.shuffled().take(count)

    }

    fun generateExercise(sentence: Sentence): SentenceExercise {
//        val words = sentence.sentence.split(" ")
        val words = sentence.sentence
            .replace(Regex("[.,!?;:]"), "")
            .split(" ")
            .filter { it.isNotEmpty() }
        return SentenceExercise(
            original = sentence.sentence,
            translation = sentence.translation,
            description = sentence.description,
            correctWords = words,
            shuffledWords = words.shuffled(),
            userWords = emptyList(),
            availableWords = words.shuffled()
        )
    }
}


data class Sentence(
    val id: Long,
    val sentence: String,
    val translation: String? = null,
    val description: String? = null,

)
