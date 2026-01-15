package com.example.german_server.data.entities



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words_otherwords",
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
data class OtherWord(
    @PrimaryKey
    @ColumnInfo(name = "word_ptr_id")
    val wordPtrId: Long,

    val word: String,

    @ColumnInfo(name = "word_translate")
    val wordTranslate: String
)
