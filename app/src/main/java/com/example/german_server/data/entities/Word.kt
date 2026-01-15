package com.example.german_server.data.entities



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words_word",
    /*foreignKeys = [
        ForeignKey(
            entity = Lection::class,
            parentColumns = ["id"],
            childColumns = ["lection_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordType::class,
            parentColumns = ["id"],
            childColumns = ["word_type_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],*/
    indices = [
        Index(value = ["lection_id"]),
        Index(value = ["word_type_id"])
    ]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "lection_id")
    val lectionId: Long,

    @ColumnInfo(name = "word_type_id")
    val wordTypeId: Long
)
