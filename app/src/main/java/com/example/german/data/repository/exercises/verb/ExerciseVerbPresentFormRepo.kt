package com.example.german.data.repository.exercises.verb

import android.util.Log
import com.example.german.data.dao.VerbDao
import com.example.german.data.entities.exercises.VerbFormExercise

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseVerbPresentFormRepoRepository(
    private val verbDao: VerbDao,
) {

    suspend fun getRandomVerbs(count: Int = 5):
            List<WordWithPresentForm> = withContext(Dispatchers.IO) {
        Log.e("Verb_forms_", "Repo")
        val verbs = mutableListOf<WordWithPresentForm>()
        Log.e("Verb_forms_", "allwords")
        verbs += verbDao.getRandomVerbs(count).map {
            WordWithPresentForm(it.wordPtrId, it.word, it.ichForm, it.duForm,
                it.erSieEsForm, it.wirForm, it.ichForm, it.sieSieForm)
        }
        verbs.shuffled().take(count)
    }

    fun generateExercises(verbs: List<WordWithPresentForm>): List<VerbFormExercise> {
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
        verb: WordWithPresentForm,
        pronoun: String
    ): String {
        return when (pronoun) {
            "ich" -> verb.ichForm
            "du" -> verb.duForm
            "er/sie/es" -> verb.erSieEsForm
            "wir" -> verb.wirForm
            "ihr" -> verb.ihrForm
            "Sie/sie" -> verb.sieSieForm
            else -> error("Unknown pronoun: $pronoun")
        }
    }

}


data class WordWithPresentForm(
    val id: Long,
    val word: String,
    val ichForm: String,
    val duForm: String,
    val erSieEsForm: String,
    val wirForm: String,
    val ihrForm: String,
    val sieSieForm: String,
)
