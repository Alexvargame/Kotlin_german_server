package com.example.german.data.entities



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words_pronoun",
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
data class Pronoun(
    @PrimaryKey
    @ColumnInfo(name = "word_ptr_id")
    val wordPtrId: Long,

    val word: String,

    @ColumnInfo(name = "word_translate")
    val wordTranslate: String,

    val akkusativ: String? = null,
    val dativ: String? = null,
    val prossessive: String? = null,
    val reflexive: String? = null,

    @ColumnInfo(name = "akkusativ_translate")
    val akkusativTranslate: String? = null,

    @ColumnInfo(name = "dativ_translate")
    val dativTranslate: String? = null,

    @ColumnInfo(name = "prossessive_translate")
    val prossessiveTranslate: String? = null,

    @ColumnInfo(name = "reflexive_translate")
    val reflexiveTranslate: String? = null
)
