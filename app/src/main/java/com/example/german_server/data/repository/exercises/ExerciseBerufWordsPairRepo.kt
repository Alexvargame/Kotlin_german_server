package com.example.german_server.data.repository.exercises

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.german_server.data.dao.*

class ExerciseBerufWordsPairRepository(
    private val nounDao: NounDao,
    private val articleDao: ArticleDao
) {

    suspend fun getRandomWords(count: Int, lection:Int): List<BerufWordWithTranslation> =  withContext(Dispatchers.IO)  {

        Log.e("WORD_PAIR_", "Repo")
        val allWords = mutableListOf<BerufWordWithTranslation>()
        Log.e("WORD_PAIR_", "allwords")
        allWords += nounDao.getRandomNounsByLection(count, lection).map {
            val article = articleDao.getById(it.articleId)
            BerufWordWithTranslation(it.wordPtrId, article = article?.name ?: "", it.word, it.wordTranslate)
        }
        allWords.shuffled().take(count)
    }
}

data class BerufWordWithTranslation(
    val id: Long,
    val article: String,
    val german: String,
    val russian: String
)
