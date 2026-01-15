package com.example.german_server.data.entities


import androidx.room.Dao
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "words_numeral",
    foreignKeys = [
        ForeignKey(
            entity = Word::class,
            parentColumns = ["id"],
            childColumns = ["word_ptr_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["word_ptr_id"])
    ]
)

data class Numeral(
    @PrimaryKey
    @ColumnInfo(name = "word_ptr_id")
    val wordPtrId: Long,

    val word: String,

    @ColumnInfo(name = "word_translate")
    val wordTranslate: String,

    val ordinal: String? = null,
    @ColumnInfo(name = "date_numeral")
    val dateNumeral: String? = null
)
