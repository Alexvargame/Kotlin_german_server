package com.example.german

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import com.example.german.data.entities.Verb
import com.example.german.data.entities.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestInsertWordBVerb(private val context: Context) {

    fun testInsertWord() {
        CoroutineScope(Dispatchers.IO).launch {

            val db = AppDatabase.Companion.getInstance(context)
            val wordDao = db.wordDao()

           val wordId = wordDao.insert(
                Word(
                    lectionId = 1,//lectionId,      // ← СУЩЕСТВУЮЩИЙ
                    wordTypeId = 3//nounTypeId     // ← WordType "Noun"
                )
            )
            val verbDao = db.verbDao()
            Log.d("STEP1", "Inserted Word id = $wordId")

            /*val verbs = listOf(
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 2)),
                    word = "gehen",
                    wordTranslate = "идти",
                    ichForm = "gehe",
                    duForm = "gehst",
                    erSieEsForm = "geht",
                    wirForm = "gehen",
                    ihrForm = "geht",
                    sieSieForm = "gehen",
                    pastPerfectForm = "gegangen",
                    pastPrateritumIchForm = "ging",
                    pastPrateritumDuForm = "gingst",
                    pastPrateritumErSieEsForm = "ging",
                    pastPrateritumWirForm = "gingen",
                    pastPrateritumIhrForm = "gingt",
                    pastPrateritumSieSieForm = "gingen",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 11, wordTypeId = 2)),
                    word = "sehen",
                    wordTranslate = "видеть",
                    ichForm = "sehe",
                    duForm = "siehst",
                    erSieEsForm = "sieht",
                    wirForm = "sehen",
                    ihrForm = "seht",
                    sieSieForm = "sehen",
                    pastPerfectForm = "gesehen",
                    pastPrateritumIchForm = "sah",
                    pastPrateritumDuForm = "sahst",
                    pastPrateritumErSieEsForm = "sah",
                    pastPrateritumWirForm = "sahen",
                    pastPrateritumIhrForm = "saht",
                    pastPrateritumSieSieForm = "sahen",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 2)),
                    word = "machen",
                    wordTranslate = "делать",
                    ichForm = "mache",
                    duForm = "machst",
                    erSieEsForm = "macht",
                    wirForm = "machen",
                    ihrForm = "macht",
                    sieSieForm = "machen",
                    pastPerfectForm = "gemacht",
                    pastPrateritumIchForm = "machte",
                    pastPrateritumDuForm = "machtest",
                    pastPrateritumErSieEsForm = "machte",
                    pastPrateritumWirForm = "machten",
                    pastPrateritumIhrForm = "machtet",
                    pastPrateritumSieSieForm = "machten",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 2)),
                    word = "lesen",
                    wordTranslate = "читать",
                    ichForm = "lese",
                    duForm = "liest",
                    erSieEsForm = "liest",
                    wirForm = "lesen",
                    ihrForm = "lest",
                    sieSieForm = "lesen",
                    pastPerfectForm = "gelesen",
                    pastPrateritumIchForm = "las",
                    pastPrateritumDuForm = "last",
                    pastPrateritumErSieEsForm = "las",
                    pastPrateritumWirForm = "lasen",
                    pastPrateritumIhrForm = "last",
                    pastPrateritumSieSieForm = "lasen",
                    regal = false
                )
            )

            verbs.forEach { verb ->
                verbDao.insert(verb)
                Log.d("STEP2", "Inserted Verb: ${verb.word}")
            }
            */
            val moreVerbs = listOf(
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 1, wordTypeId = 2)),
                    word = "schreiben",
                    wordTranslate = "писать",
                    ichForm = "schreibe",
                    duForm = "schreibst",
                    erSieEsForm = "schreibt",
                    wirForm = "schreiben",
                    ihrForm = "schreibt",
                    sieSieForm = "schreiben",
                    pastPerfectForm = "geschrieben",
                    pastPrateritumIchForm = "schrieb",
                    pastPrateritumDuForm = "schriebst",
                    pastPrateritumErSieEsForm = "schrieb",
                    pastPrateritumWirForm = "schrieben",
                    pastPrateritumIhrForm = "schriebt",
                    pastPrateritumSieSieForm = "schrieben",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 2, wordTypeId = 2)),
                    word = "sprechen",
                    wordTranslate = "говорить",
                    ichForm = "spreche",
                    duForm = "sprichst",
                    erSieEsForm = "spricht",
                    wirForm = "sprechen",
                    ihrForm = "sprecht",
                    sieSieForm = "sprechen",
                    pastPerfectForm = "gesprochen",
                    pastPrateritumIchForm = "sprach",
                    pastPrateritumDuForm = "sprachst",
                    pastPrateritumErSieEsForm = "sprach",
                    pastPrateritumWirForm = "sprachen",
                    pastPrateritumIhrForm = "spracht",
                    pastPrateritumSieSieForm = "sprachen",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 2, wordTypeId = 2)),
                    word = "kommen",
                    wordTranslate = "приходить",
                    ichForm = "komme",
                    duForm = "kommst",
                    erSieEsForm = "kommt",
                    wirForm = "kommen",
                    ihrForm = "kommt",
                    sieSieForm = "kommen",
                    pastPerfectForm = "gekommen",
                    pastPrateritumIchForm = "kam",
                    pastPrateritumDuForm = "kamst",
                    pastPrateritumErSieEsForm = "kam",
                    pastPrateritumWirForm = "kamen",
                    pastPrateritumIhrForm = "kamt",
                    pastPrateritumSieSieForm = "kamen",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 2, wordTypeId = 2)),
                    word = "trinken",
                    wordTranslate = "пить",
                    ichForm = "trinke",
                    duForm = "trinkst",
                    erSieEsForm = "trinkt",
                    wirForm = "trinken",
                    ihrForm = "trinkt",
                    sieSieForm = "trinken",
                    pastPerfectForm = "getrunken",
                    pastPrateritumIchForm = "trank",
                    pastPrateritumDuForm = "trankst",
                    pastPrateritumErSieEsForm = "trank",
                    pastPrateritumWirForm = "tranken",
                    pastPrateritumIhrForm = "trankt",
                    pastPrateritumSieSieForm = "tranken",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 2, wordTypeId = 2)),
                    word = "fahren",
                    wordTranslate = "ехать",
                    ichForm = "fahre",
                    duForm = "fährst",
                    erSieEsForm = "fährt",
                    wirForm = "fahren",
                    ihrForm = "fahrt",
                    sieSieForm = "fahren",
                    pastPerfectForm = "gefahren",
                    pastPrateritumIchForm = "fuhr",
                    pastPrateritumDuForm = "fuhrst",
                    pastPrateritumErSieEsForm = "fuhr",
                    pastPrateritumWirForm = "fuhren",
                    pastPrateritumIhrForm = "fuhrt",
                    pastPrateritumSieSieForm = "fuhren",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 3, wordTypeId = 2)),
                    word = "arbeiten",
                    wordTranslate = "работать",
                    ichForm = "arbeite",
                    duForm = "arbeitest",
                    erSieEsForm = "arbeitet",
                    wirForm = "arbeiten",
                    ihrForm = "arbeitet",
                    sieSieForm = "arbeiten",
                    pastPerfectForm = "gearbeitet",
                    pastPrateritumIchForm = "arbeitete",
                    pastPrateritumDuForm = "arbeitetest",
                    pastPrateritumErSieEsForm = "arbeitete",
                    pastPrateritumWirForm = "arbeiteten",
                    pastPrateritumIhrForm = "arbeitetet",
                    pastPrateritumSieSieForm = "arbeiteten",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 3, wordTypeId = 2)),
                    word = "spielen",
                    wordTranslate = "играть",
                    ichForm = "spiele",
                    duForm = "spielst",
                    erSieEsForm = "spielt",
                    wirForm = "spielen",
                    ihrForm = "spielt",
                    sieSieForm = "spielen",
                    pastPerfectForm = "gespielt",
                    pastPrateritumIchForm = "spielte",
                    pastPrateritumDuForm = "spieltest",
                    pastPrateritumErSieEsForm = "spielte",
                    pastPrateritumWirForm = "spielten",
                    pastPrateritumIhrForm = "spieltet",
                    pastPrateritumSieSieForm = "spielten",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 3, wordTypeId = 2)),
                    word = "laufen",
                    wordTranslate = "бегать",
                    ichForm = "laufe",
                    duForm = "läufst",
                    erSieEsForm = "läuft",
                    wirForm = "laufen",
                    ihrForm = "lauft",
                    sieSieForm = "laufen",
                    pastPerfectForm = "gelaufen",
                    pastPrateritumIchForm = "lief",
                    pastPrateritumDuForm = "liefst",
                    pastPrateritumErSieEsForm = "lief",
                    pastPrateritumWirForm = "liefen",
                    pastPrateritumIhrForm = "lieft",
                    pastPrateritumSieSieForm = "liefen",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 3, wordTypeId = 2)),
                    word = "essen",
                    wordTranslate = "есть",
                    ichForm = "esse",
                    duForm = "isst",
                    erSieEsForm = "isst",
                    wirForm = "essen",
                    ihrForm = "esst",
                    sieSieForm = "essen",
                    pastPerfectForm = "gegessen",
                    pastPrateritumIchForm = "aß",
                    pastPrateritumDuForm = "aßest",
                    pastPrateritumErSieEsForm = "aß",
                    pastPrateritumWirForm = "aßen",
                    pastPrateritumIhrForm = "aßt",
                    pastPrateritumSieSieForm = "aßen",
                    regal = false
                ),
                Verb(
                    wordPtrId = wordDao.insert(Word(lectionId = 3, wordTypeId = 2)),
                    word = "laufen",
                    wordTranslate = "ходить",
                    ichForm = "laufe",
                    duForm = "läufst",
                    erSieEsForm = "läuft",
                    wirForm = "laufen",
                    ihrForm = "lauft",
                    sieSieForm = "laufen",
                    pastPerfectForm = "gelaufen",
                    pastPrateritumIchForm = "lief",
                    pastPrateritumDuForm = "liefst",
                    pastPrateritumErSieEsForm = "lief",
                    pastPrateritumWirForm = "liefen",
                    pastPrateritumIhrForm = "lieft",
                    pastPrateritumSieSieForm = "liefen",
                    regal = false
                )
            )
            moreVerbs.forEach { verb ->
                verbDao.insert(verb)
                Log.d("STEP2", "Inserted Verb: ${verb.word}")
            }

            Log.d("STEP2", "Inserted Verb id = ")
            verbDao.getAll().forEach {
                Log.d("VERB_2", "Verb row: $it")
            }
        }
    }
}