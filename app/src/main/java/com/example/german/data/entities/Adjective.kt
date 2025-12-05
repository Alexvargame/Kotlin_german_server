package com.example.german.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words_adjective",
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
data class Adjective(
    @PrimaryKey
    @ColumnInfo(name = "word_ptr_id")
    val wordPtrId: Long,

    val word: String,
    @ColumnInfo(name = "word_translate")
    val wordTranslate: String,

    val komparativ: String? = null,
    val superlativ: String? = null,

    val declensions: String? = null // JSON храним как строку
)
