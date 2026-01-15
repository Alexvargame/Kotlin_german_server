package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.gson.Gson

import com.example.german_server.data.entities.Article

class Add_articles(private val context: Context) {

    fun addarticles() {
        Log.d("TEST_DB", " Context ${context}")
        //AppDatabase.resetInstance()
        //context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        //Log.d("TEST_DB_ARTICLE", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val articleDao = db.articleDao()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_DB", "testAllWordRelatedTables() started")

            val gson = Gson()

            val formsMap = mapOf("akk" to "den", "dat" to "dem", "gen" to "des")
            val formsJson = gson.toJson(formsMap)

            val article1 = Article(
                name = "der",
                description = "Мужской",
                forms = formsJson
            )
            articleDao.insert(article1)
            Log.d("Article1", "Новая  Art1 вставлен")
            val gson1 = Gson()

            val formsMap1 = mapOf("akk" to "das", "dat" to "dem", "gen" to "des")
            val formsJson1= gson.toJson(formsMap1)

            val article2  = Article(
                name = "das",
                description = "Средний",
                forms = formsJson1
            )
            articleDao.insert(article2)
            Log.d("Article2", "Новая  Art2 вставлен")

            val gson2 = Gson()

            val formsMap2 = mapOf("akk" to "die", "dat" to "der", "gen" to "der")
            val formsJson2 = gson.toJson(formsMap2)

            val article3  = Article(
                name = "die",
                description = "Женский",
                forms = formsJson2
            )
            articleDao.insert(article3)
            Log.d("Article3", "Новая  Art3 вставлен")

            val gson4 = Gson()

            val formsMap4 = mapOf("akk" to "die", "dat" to "den", "gen" to "der")
            val formsJson4 = gson.toJson(formsMap4)

            val article4  = Article(
                name = "die.",
                description = "Plural",
                forms = formsJson4
            )
            articleDao.insert(article4)
            Log.d("Article4", "Новая  Art4 вставлен")

            val articles = articleDao.getAll()
            articles.forEach {
                Log.d("ARTICLE_DB", "USER_ROLE: ${it.name} / ${it.description}/ ${it.id}")
            }
        }
    }
}


