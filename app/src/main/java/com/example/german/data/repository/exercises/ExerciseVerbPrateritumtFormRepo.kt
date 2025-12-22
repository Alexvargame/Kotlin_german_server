package com.example.german.data.repository.exercises

import android.util.Log
import com.example.german.data.dao.VerbDao
import com.example.german.data.entities.exercises.VerbFormExercise

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseVerbPrateritumFormRepoRepository(
    private val verbDao: VerbDao,
) {

    suspend fun getRandomVerbs(count: Int = 5):
            List<WordWithPrateritumtForm> = withContext(Dispatchers.IO) {
        Log.e("Verb_forms_", "Repo")
        val verbs = mutableListOf<WordWithPrateritumtForm>()
        Log.e("Verb_forms_", "allwords")
        verbs += verbDao.getRandomVerbs(count).map {
            WordWithPrateritumtForm(it.wordPtrId, it.word, it.pastPrateritumIchForm,
                it.pastPrateritumDuForm, it.pastPrateritumErSieEsForm,
                it.pastPrateritumWirForm, it.pastPrateritumIhrForm,
                it.pastPrateritumSieSieForm)
        }
        verbs.shuffled().take(count)
    }

    fun generateExercises(verbs: List<WordWithPrateritumtForm>): List<VerbFormExercise> {
        val pronouns = listOf("ich","du","er/sie/es","wir","ihr","Sie/sie")
        val randomPronouns: List<String> =
            List(verbs.size) { pronouns.random() }
        Log.e("Verb_form_", "repo")
        return verbs.mapIndexed { index, verb ->
            VerbFormExercise(
                verbId = verb.id,
                infinitive = verb.word,
                pronoun = randomPronouns[index],
                correctForm = getCorrectForm(verb, randomPronouns[index]),
                variants = null,
                userAnswer = null
            )
        }
    }
    fun getCorrectForm(
        verb: WordWithPrateritumtForm,
        pronoun: String
    ): String {
        val form: String? =  when (pronoun) {
            "ich" -> verb.pastPrateritumIchForm
            "du" -> verb.pastPrateritumDuForm
            "er/sie/es" -> verb.pastPrateritumErSieEsForm
            "wir" -> verb.pastPrateritumWirForm
            "ihr" -> verb.pastPrateritumIhrForm
            "Sie/sie" -> verb.pastPrateritumSieSieForm
            else -> null
        }
        return form ?: ""
    }

}


data class WordWithPrateritumtForm(
    val id: Long,
    val word: String,
    val pastPrateritumIchForm: String?,
    val pastPrateritumDuForm: String?,
    val pastPrateritumErSieEsForm: String?,
    val pastPrateritumWirForm: String?,
    val pastPrateritumIhrForm: String?,
    val pastPrateritumSieSieForm: String?,
)
