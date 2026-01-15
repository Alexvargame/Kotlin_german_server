package com.example.german_server.data.repository.exercises

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.german_server.data.dao.*

class ExerciseWordsPairRepository(
    private val nounDao: NounDao,
    private val verbDao: VerbDao,
    private val adjectiveDao: AdjectiveDao,
    private val numeralDao: NumeralDao,
    private val pronounDao: PronounDao,
    private val otherWordDao: OtherWordDao
) {

    suspend fun getRandomWords(count: Int): List<WordWithTranslation> =  withContext(Dispatchers.IO)  {

        Log.e("WORD_PAIR_", "Repo")
        val allWords = mutableListOf<WordWithTranslation>()
        Log.e("WORD_PAIR_", "allwords")
        allWords += nounDao.getRandomNouns(count).map {
            WordWithTranslation(it.wordPtrId, it.word, it.wordTranslate)
        }

        allWords += verbDao.getRandomVerbs(count).map {
            WordWithTranslation(it.wordPtrId, it.word, it.wordTranslate)
        }

        allWords += adjectiveDao.getRandomAdjectives(count).map {
            WordWithTranslation(it.wordPtrId, it.word, it.wordTranslate)
        }
        allWords += numeralDao.getRandomNumerals(count).map {
            WordWithTranslation(it.wordPtrId, it.word, it.wordTranslate)
        }
        allWords += pronounDao.getRandomPronouns(count).map {
            WordWithTranslation(it.wordPtrId, it.word, it.wordTranslate)
        }
        allWords += otherWordDao.getRandomOtherWords(count).map {
            WordWithTranslation(it.wordPtrId, it.word, it.wordTranslate)
        }

        allWords.shuffled().take(count)
    }
}

data class WordWithTranslation(
    val id: Long,
    val german: String,
    val russian: String
)
