package com.example.german.data.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words_noun",
    foreignKeys = [
        ForeignKey(
            entity = Word::class,
            parentColumns = ["id"],
            childColumns = ["word_ptr_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Article::class,
            parentColumns = ["id"],
            childColumns = ["article_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["article_id"])
    ]
)
data class Noun(
    @PrimaryKey
    @ColumnInfo(name = "word_ptr_id")
    val wordPtrId: Long,

    @ColumnInfo(name = "word_plural")
    val wordPlural: String? = null,

    @ColumnInfo(name = "plural_sign")
    val pluralSign: String? = null,

    @ColumnInfo(name = "word_translate_plural")
    val wordTranslatePlural: String? = null,

    @ColumnInfo(name = "article_id")
    val articleId: Long,

    val word: String,

    @ColumnInfo(name = "word_translate")
    val wordTranslate: String
)
