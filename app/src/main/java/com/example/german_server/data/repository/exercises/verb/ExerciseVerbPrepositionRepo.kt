package com.example.german_server.data.repository.exercises.verb

import android.util.Log
import com.example.german_server.data.dao.VerbDao
import com.example.german_server.data.entities.exercises.VerbPrepositionExercise
import com.example.german_server.data.ui.components.prepositionsMap

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


//val allPrepositions = listOf("an", "auf", "bei", "für", "gegen", "in",
//    "mit", "nach", "über", "um", "von", "vor", "zu", "aus")

class ExerciseVerbPrepositionRepository(
    private val verbDao: VerbDao,
) {

    suspend fun getRandomVerbs(count: Int = 5):
            List<VerbWithPreposition> = withContext(Dispatchers.IO) {
        Log.e("WORD_Verb_", "Repo")
        val verbs = mutableListOf<VerbWithPreposition>()
        Log.e("WORD_Verb_", "allwords")
        verbs += verbDao.getRandomVerbsWithPreposition(count).map {
            VerbWithPreposition(it.wordPtrId, it.word, it.preposition, it.prepositionCase)
        }
        verbs.shuffled().take(count)

    }

    fun generateExercises(verbs: List<VerbWithPreposition>): List<VerbPrepositionExercise> {
        return verbs.map { verb ->
            val correctPrep = verb.preposition
            val correctCase = verb.prepositionCase  // падеж из БД (правильный)

            // Неправильные варианты
            val wrongVariants = prepositionsMap.keys
                .filter { it != correctPrep }
                .shuffled()
                .take(2)
                .map { prep ->
                    val case = prepositionsMap[prep]!!.random()
                    "$prep/$case"
                }

            // Правильный вариант
            val correctVariant = "$correctPrep/$correctCase"

            // Смешать
            val variantsAnswer = (wrongVariants + correctVariant).shuffled()

            VerbPrepositionExercise(
                word = verb.word,
                preposition = correctVariant,  // правильный ответ как "für/Akk"
                variantsAnswer = variantsAnswer,
            )
        }
    }
}

data class VerbWithPreposition(
    val id: Long,
    val word: String,
    val preposition: String?,
    val prepositionCase: String?
)

