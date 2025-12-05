package com.example.german.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words_verb",
    foreignKeys = [
        ForeignKey(
            entity = Word::class,
            parentColumns = ["id"],
            childColumns = ["word_ptr_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["word_ptr_id"])]
)
data class Verb(
    @PrimaryKey
    @ColumnInfo(name = "word_ptr_id")
    val wordPtrId: Long,

    val word: String,

    @ColumnInfo(name = "word_translate")
    val wordTranslate: String,

    @ColumnInfo(name = "ich_form")
    val ichForm: String,

    @ColumnInfo(name = "du_form")
    val duForm: String,

    @ColumnInfo(name = "er_sie_es_form")
    val erSieEsForm: String,

    @ColumnInfo(name = "wir_form")
    val wirForm: String,

    @ColumnInfo(name = "ihr_form")
    val ihrForm: String,

    @ColumnInfo(name = "Sie_sie_form")
    val sieSieForm: String,

    @ColumnInfo(name = "past_perfect_form")
    val pastPerfectForm: String? = null,

    @ColumnInfo(name = "past_prateritum_Sie_sie_form")
    val pastPrateritumSieSieForm: String? = null,

    @ColumnInfo(name = "past_prateritum_du_form")
    val pastPrateritumDuForm: String? = null,

    @ColumnInfo(name = "past_prateritum_er_sie_es_form")
    val pastPrateritumErSieEsForm: String? = null,

    @ColumnInfo(name = "past_prateritum_ich_form")
    val pastPrateritumIchForm: String? = null,

    @ColumnInfo(name = "past_prateritum_ihr_form")
    val pastPrateritumIhrForm: String? = null,

    @ColumnInfo(name = "past_prateritum_wir_form")
    val pastPrateritumWirForm: String? = null,

    val regal: Boolean
)
