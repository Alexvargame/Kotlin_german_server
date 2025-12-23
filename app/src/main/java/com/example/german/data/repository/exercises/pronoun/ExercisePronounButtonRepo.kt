package com.example.german.data.repository.exercises.pronoun

import android.util.Log
import com.example.german.data.dao.PronounDao
import com.example.german.data.entities.exercises.PronounExercise

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.String

class ExercisePronounButtonRepoRepository(
    private val pronounDao: PronounDao,
) {

    suspend fun getRandomPronouns(count: Int = 5):
            List<WordWithPronoun> = withContext(Dispatchers.IO) {
        Log.e("Pronoun_s_", "Repo")
        val pronouns = mutableListOf<WordWithPronoun>()
        Log.e("Verb_forms_", "allwords")
        pronouns += pronounDao.getRandomPronouns(count).map {
            WordWithPronoun(it.wordPtrId, it.word, it.akkusativ,
                it.dativ, it.prossessive, it.reflexive)
        }
        pronouns.shuffled().take(count)
    }

    fun generateExercises(pronouns: List<WordWithPronoun>): List<PronounExercise> {
        val casus = listOf("akkusativ", "dativ", "prossessive", "reflexive")
        val randomCasus: List<String> =
            List(pronouns.size) { casus.random() }
        val pronounForms = listOf("mich", "mir", "mein", "dich", "dir", "dein","ihn",
            "ihm", "sein", "sich", "sie", "ihr", "es", "uns", "unser", "euch", "euer",
            "ihnen", "Sie", "Ihnen","Ihr",
        )
        Log.e("Pronouns_", "repo")
        return pronouns.mapIndexed { index, pronoun ->
            val correct = getCorrectCasus(pronoun,randomCasus[index])
            PronounExercise(
                verbId = pronoun.id,
                word = pronoun.word,
                casus = randomCasus[index],
                correctForm = correct,
                variants = (pronounForms - correct)
                    .shuffled()
                    .take(3)
                    .toMutableList()
                    .apply { add(correct) }
                    .shuffled(),
                userAnswer = null
            )
        }
    }
    fun getCorrectCasus(
        pronoun: WordWithPronoun,
        casus: String
    ): String {
        val form: String? =  when (casus) {
            "akkusativ" -> pronoun.akkusativ
            "dativ" -> pronoun.dativ
            "prossessive" -> pronoun.prossessive
            "reflexive" -> pronoun.reflexive
            else -> null
        }
        return form ?: ""
    }


}


/*data class WordWithPronoun(
    val id: Long,
    val word: String,
    val akkusativ: String?,
    val dativ: String?,
    val prossessive: String?,
    val reflexive: String?,

)*/
