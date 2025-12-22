package com.example.german.data.repository.exercises

import android.util.Log
import com.example.german.data.dao.NounDao
import com.example.german.data.entities.Article
import com.example.german.data.entities.exercises.ArticleExercise

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseArticleRepository(
    private val nounDao: NounDao,
) {

    suspend fun getRandomNouns(count: Int = 5):
            List<WordWithArticle> = withContext(Dispatchers.IO) {
        Log.e("WORD_PAIR_", "Repo")
        val nouns = mutableListOf<WordWithArticle>()
        Log.e("WORD_PAIR_", "allwords")
        nouns += nounDao.getRandomNouns(count).map {
            WordWithArticle(it.wordPtrId, it.word, it.articleId)
        }
        nouns.shuffled().take(count)

    }

    fun generateExercises(nouns: List<WordWithArticle>): List<ArticleExercise> {
        val variantsAnswer = listOf(Article(id = 1, name = "der", description = "мужской"),
            Article(id = 3, name = "die", description = "женский"),
            Article(id = 2, name = "das", description = "средний"),
            Article(id = 4, name = "die (Plural)", description = "множественное число")
        )
        Log.e("Article_", "repo")
        return nouns.map { noun ->
            ArticleExercise(
                word = noun.word,
                article = noun.article,
                variantsAnswer = variantsAnswer
            )
        }
    }
}


data class WordWithArticle(
    val id: Long,
    val word: String,
    val article: Long,
)
