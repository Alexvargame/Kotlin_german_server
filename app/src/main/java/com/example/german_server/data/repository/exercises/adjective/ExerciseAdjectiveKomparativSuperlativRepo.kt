package com.example.german_server.data.repository.exercises.adjective

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.german_server.data.dao.AdjectiveDao
import com.example.german_server.data.entities.exercises.AdjectiveKomparativSuperlativExercise

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseAdjectiveKomparativSuperlativRepotory(
    private val adjectiveDao: AdjectiveDao,
) {

    suspend fun getRandomAdjectives(count: Int = 5):
            List<WordWithKomparativSuperlativ> = withContext(Dispatchers.IO) {
        Log.e("Adj_forms_", "Repo")
        val adjs = mutableListOf<WordWithKomparativSuperlativ>()
        Log.e("Adj_forms_", "allwords")
        adjs += adjectiveDao.getRandomAdjectives(count).map {
            WordWithKomparativSuperlativ(
                it.wordPtrId, it.word,
                it.komparativ, it.superlativ
            )
        }
        adjs.shuffled().take(count)
    }

    fun generateExercises(adjs: List<WordWithKomparativSuperlativ>):
            List<AdjectiveKomparativSuperlativExercise> {

        var adj_form by mutableStateOf<String?>(null)
        return adjs.mapIndexed { index, adj ->
            if (listOf("komparativ", "superlativ").random() == "komparativ") {
                adj_form = adj.komparativeForm
            } else {
                adj_form = adj.superlativForm
            }
            val question =
                "${adj.word} - ${adj_form} ?"
            AdjectiveKomparativSuperlativExercise(
                adjectiveId = adj.id,
                word = adj.word,
                question = question,
                correctForm = adj_form ?: "",
                userAnswer = null
            )
        }
    }
}


data class WordWithKomparativSuperlativ(
    val id: Long,
    val word: String,
    val komparativeForm: String?,
    val superlativForm: String?,

)
