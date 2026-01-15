package com.example.german_server.data.repository.exercises.verb

import android.util.Log
import com.example.german_server.data.dao.VerbDao
import com.example.german_server.data.entities.exercises.VerbFormExercise

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseVerbPerfectFormRepoRepository(
    private val verbDao: VerbDao,
) {

    suspend fun getRandomVerbs(count: Int = 5):
            List<WordWithPerfectForm> = withContext(Dispatchers.IO) {
        Log.e("Verb_forms_", "Repo")
        val verbs = mutableListOf<WordWithPerfectForm>()
        Log.e("Verb_forms_", "allwords")
        verbs += verbDao.getRandomVerbs(count).map {
            WordWithPerfectForm(it.wordPtrId, it.word, it.pastPerfectForm)
        }
        verbs.shuffled().take(count)
    }

    fun generateExercises(verbs: List<WordWithPerfectForm>): List<VerbFormExercise> {
        val pronouns = listOf("ich","du","er/sie/es","wir","ihr","Sie/sie")
        val randomPronouns: List<String> =
            List(verbs.size) { pronouns.random() }
        Log.e("Verb_form_", "repo")
        return verbs.mapIndexed { index, verb ->
            VerbFormExercise(
                verbId = verb.id,
                infinitive = verb.word,
                pronoun = randomPronouns[index],
                correctForm = verb.pastPerfectForm ?: "",
                variants = null,
                userAnswer = null
            )
        }
    }


}


data class WordWithPerfectForm(
    val id: Long,
    val word: String,
    val pastPerfectForm: String?,

)
