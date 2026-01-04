package com.example.german.data.repository.exercises.adjective

import android.util.Log
import com.example.german.data.dao.AdjectiveDao
import com.example.german.data.dao.NounDeclensionsFormDao
import com.example.german.data.dao.NounDao
import com.example.german.data.dao.ArticleDao
import com.example.german.data.entities.NounDeclensionsForm
import com.example.german.data.entities.exercises.ExerciseAdjectiveCasus
import com.google.gson.Gson

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.Long

class ExerciseAdjectiveCasusRepository(
    private val nounDeclensionsFormDao: NounDeclensionsFormDao,
    private val adjectiveDao: AdjectiveDao,
    private val articleDao: ArticleDao,
    private val nounDao: NounDao
) {
    val casus_articles: Map<String, List<String>> = mapOf(
        "ein" to listOf("ein", "eine", "einen", "einem", "eines", "einer"),
        "kein" to listOf("kein", "keine", "keinen", "keinem", "keines", "keiner"),
        "definite" to listOf("der", "die", "das", "den", "dem", "des")
    )
    val articles = mapOf(
        "der" to "Maskulinum",
        "das" to "Neutrum",
        "die" to "Femininum"
    )
    suspend fun getRandomNounDeclensionsForm(count: Int = 1):
            List<WordWithDeclensionsForm> = withContext(Dispatchers.IO) {

        nounDeclensionsFormDao.getRandomNounDeclensionsForm(count).map { form ->
            val nounOptions = listOfNotNull(
                form.nominativ,
                form.akkusativ,
                form.dativ,
                form.genitiv,
                form.pluralNominativ,
                form.pluralAkkusativ,
                form.pluralDativ,
                form.pluralGenitiv
            )
                .distinct()
                .shuffled()
            val nounDecl = withContext(Dispatchers.IO) {
                nounDeclensionsFormDao.getById(form.id)
            } ?: throw IllegalArgumentException("Noun not found for id=${form.id}")
            WordWithDeclensionsForm(
                id = form.nounId,
                obj = nounDecl,
                nounOptions = nounOptions
            )

        }
    }
    suspend fun getRandomAdjectiveForm(count: Int = 1):
            List<WordWithAdjectiveForm> = withContext(Dispatchers.IO) {

        adjectiveDao.getRandomAdjectives(count).map { adj ->

            // парсим JSON из поля declensions
            val gson = Gson()
            //val declensionsMap: Map<String, Any> = gson.fromJson(adj.declensions, Map::class.java)
            val declensionsMap: Map<*, *> = gson.fromJson(adj.declensions, Map::class.java)


            // собираем все формы во все падежи / роды / артикли
            val adjectiveList = mutableSetOf<String>()

            val stark = declensionsMap["stark"] as? Map<*, *> ?: emptyMap<Any, Any>()

            for ((caseKey, caseValue) in stark) {
                val caseMap = caseValue as? Map<*, *> ?: continue

                for ((genderKey, genderValue) in caseMap) {
                    val genderMap = genderValue as? Map<*, *> ?: continue

                    for ((articleKey, formValue) in genderMap) {
                        val formStr = formValue as? String ?: continue
                        adjectiveList.add(formStr)
                    }
                }
            }
            val adjectiveOptions = adjectiveList.toList().distinct().shuffled()
            // Вывод на печать в лог Android
            Log.e("AdjectiveOptions", adjectiveOptions.joinToString(", "))

// Или просто в консоль (для тестов)
            println("AdjectiveOptions: ${adjectiveOptions.joinToString(", ")}")

            WordWithAdjectiveForm(
                id = adj.wordPtrId,
                adjectiveOptions = adjectiveOptions
            )
        }
    }
    suspend fun getCorrectArticleAndAdjectiveForm(
        adjectiveForm: WordWithAdjectiveForm,
        case: String,
        gender: String,
        articleGroup: String
    ): Pair<String, String> = withContext(Dispatchers.IO) {

        val adjInfo = adjectiveDao.getById(adjectiveForm.id)
            ?: throw IllegalArgumentException("Adjective not found id=${adjectiveForm.id}")

        // Парсим JSON точно так же, как в рабочем примере
        val gson = Gson()
        val declensionsMap: Map<*, *> = gson.fromJson(adjInfo.declensions, Map::class.java)

        val stark = declensionsMap["stark"] as? Map<*, *> ?: emptyMap<Any, Any>()
        val caseMap = stark[case] as? Map<*, *> ?: emptyMap<Any, Any>()
        val genderMap = caseMap[gender] as? Map<*, *> ?: emptyMap<Any, Any>()

        // Определяем артикль по articleGroup
        val articleKey = when(articleGroup) {
            "definite" -> genderMap.keys.firstOrNull { it.toString().startsWith("d") }
            "ein" -> genderMap.keys.firstOrNull { it.toString().startsWith("e") }
            "kein" -> genderMap.keys.firstOrNull { it.toString().startsWith("k") }
            else -> error("Unknown articleGroup=$articleGroup")
        } ?: error("No matching article found")

        val correctAdjectiveForm = genderMap[articleKey] as? String
            ?: error("No form for article $articleKey")

        articleKey.toString() to correctAdjectiveForm
    }

    fun getDeclinedNounForm(
        obj: NounDeclensionsForm,
        case: String,
        number: String // "Singular" или "Plural"
    ): String {

        return if (number == "Plural") {
            when (case) {
                "Nominativ" -> obj.pluralNominativ
                "Akkusativ" -> obj.pluralAkkusativ
                "Dativ" -> obj.pluralDativ
                "Genitiv" -> obj.pluralGenitiv
                else -> error("Unknown case=$case")
            }
        } else {
            when (case) {
                "Nominativ" -> obj.nominativ
                "Akkusativ" -> obj.akkusativ
                "Dativ" -> obj.dativ
                "Genitiv" -> obj.genitiv
                else -> error("Unknown case=$case")
            }
        } ?: error("No form found for case=$case, number=$number")
    }

    suspend fun generateExercises(nounForm: WordWithDeclensionsForm,
                          adjectiveForm: WordWithAdjectiveForm,
                          ): ExerciseAdjectiveCasus {

        Log.e("Article_", "repo")

        val nounInfo = withContext(Dispatchers.IO) {
            nounDao.getById(nounForm.id)
        } ?: throw IllegalArgumentException("Noun not found for id=${nounForm.id}")

        val adjectiveInfo = withContext(Dispatchers.IO) {
            adjectiveDao.getById(adjectiveForm.id)
        } ?: throw IllegalArgumentException("Noun not found for id=${adjectiveForm.id}")

        val number = if (nounInfo.wordPlural == "nan") "Plural"
            else listOf("Singular", "Plural").random()

        // 3. Определяем группу артиклей
        val articleGroup = if (number == "Plural") listOf("definite", "kein").random()
        else listOf("definite", "ein").random()

        val articleOptions = casus_articles[articleGroup]?.shuffled() ?: emptyList()

        // 5. Гендер


        val article = withContext(Dispatchers.IO) {
            articleDao.getById(nounInfo.articleId ?: 1)
        } ?: throw IllegalArgumentException("Article not found for id=${nounInfo.articleId}")

        val gender = if (number == "Plural") "Plural"
            else articles[article.name] ?: "Maskulinum"

        // 6. Падеж
        val cases = listOf("Nominativ", "Akkusativ", "Dativ", "Genitiv")
        val case = cases.random()

        // 7. Определяем правильные формы
        val correctNounForm = getDeclinedNounForm(nounForm.obj, case, number)
        val (correctArticle, correctAdjectiveForm) = getCorrectArticleAndAdjectiveForm(
            adjectiveForm, case, gender, articleGroup
        )

        val nounWordForQuestion =
            if (number == "Plural")
                nounInfo.wordPlural
            else
                nounInfo.word

        val question =
            "$articleGroup ${adjectiveInfo.word} ${nounWordForQuestion} $case"
        Log.e("EXERCISE","${question} ${correctArticle} ")

        return ExerciseAdjectiveCasus(
            noun = nounForm.id,
            adjective = adjectiveForm.id,
            gender = gender,
            case = case,
            articleOptions = articleOptions,
            adjectiveOptions = adjectiveForm.adjectiveOptions,
            nounOptions = nounForm.nounOptions,
            question = question,
            correctArticle = correctArticle,
            correctAdjectiveForm = correctAdjectiveForm,
            correctNounForm = correctNounForm,
            selectedArticle = null,
            selectedAdjective = null,
            selectedNoun = null
        )
    }
}


data class WordWithDeclensionsForm(
    val id: Long,
    val obj: NounDeclensionsForm,
    val nounOptions: List<String>,

)

data class WordWithAdjectiveForm(
    val id: Long,
    val adjectiveOptions: List<String>,
    )
