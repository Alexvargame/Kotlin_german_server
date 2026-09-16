package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.entities.Verb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
class Add_verbs_prep(private val context: Context) {

    fun add_verb_prep() {
        val db = AppDatabase.getInstance(context)
        val sqlite = db.openHelper.writableDatabase

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_VERBSp_pre", "started")

            val updates = listOf(
                "achten" to ("auf" to "Akk"),
                "anfangen" to ("mit" to "Dat"),
                "ankommen" to ("auf" to "Akk"),
                "anrufen" to ("bei" to "Dat"),
                "antworten" to ("auf" to "Akk"),
                "arbeiten" to ("an" to "Dat"),
                "ärgern" to ("über" to "Akk"),
                "aufhören" to ("mit" to "Dat"),
                "aufpassen" to ("auf" to "Akk"),
                "bedanken" to ("bei" to "Dat"),
                "beginnen" to ("mit" to "Dat"),
                "beschäftigen" to ("mit" to "Dat"),
                "beschweren" to ("über" to "Akk"),
                "bestehen" to ("aus" to "Dat"),
                "bewerben" to ("um" to "Akk"),
                "bitten" to ("um" to "Akk"),
                "danken" to ("für" to "Akk"),
                "denken" to ("an" to "Akk"),
                "diskutieren" to ("über" to "Akk"),
                "entschuldigen" to ("bei" to "Dat"),
                "erinnern" to ("an" to "Akk"),
                "erkundigen" to ("nach" to "Dat"),
                "erzählen" to ("von" to "Dat"),
                "fragen" to ("nach" to "Dat"),
                "freuen" to ("auf" to "Akk"),
                "fürchten" to ("vor" to "Dat"),
                "gehören" to ("zu" to "Dat"),
                "gewöhnen" to ("an" to "Akk"),
                "glauben" to ("an" to "Akk"),
                "gratulieren" to ("zu" to "Dat"),
                "halten" to ("von" to "Dat"),
                "handeln" to ("von" to "Dat"),
                "helfen" to ("bei" to "Dat"),
                "hoffen" to ("auf" to "Akk"),
                "interessieren" to ("für" to "Akk"),
                "kämpfen" to ("für" to "Akk"),
                "konzentrieren" to ("auf" to "Akk"),
                "kümmern" to ("um" to "Akk"),
                "lachen" to ("über" to "Akk"),
                "leiden" to ("an" to "Dat"),
                "nachdenken" to ("über" to "Akk"),
                "protestieren" to ("gegen" to "Akk"),
                "reagieren" to ("auf" to "Akk"),
                "rechnen" to ("mit" to "Dat"),
                "riechen" to ("nach" to "Dat"),
                "schmecken" to ("nach" to "Dat"),
                "schützen" to ("vor" to "Dat"),
                "sorgen" to ("für" to "Akk"),
                "sprechen" to ("mit" to "Dat"),
                "sterben" to ("an" to "Dat"),
                "teilnehmen" to ("an" to "Dat"),
                "telefonieren" to ("mit" to "Dat"),
                "träumen" to ("von" to "Dat"),
                "unterhalten" to ("mit" to "Dat"),
                "verabreden" to ("mit" to "Dat"),
                "verabschieden" to ("von" to "Dat"),
                "verlassen" to ("auf" to "Akk"),
                "verlieben" to ("in" to "Akk"),
                "warnen" to ("vor" to "Dat"),
                "warten" to ("auf" to "Akk"),
                "zweifeln" to ("an" to "Dat")
            )

            updates.forEach { (word, prep) ->
                sqlite.execSQL(
                    "UPDATE words_verb SET preposition = ?, preposition_case = ? WHERE word = ?",
                    arrayOf(prep.first, prep.second, word)
                )
            }

            Log.d("TEST_VERBSp_pre", "Обновлено: ${updates.size}")
        }
    }
}