package com.example.german_server.data.entities



import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words_noundeclensionsform",
    foreignKeys = [
        ForeignKey(
            entity = Noun::class,
            parentColumns = ["word_ptr_id"],
            childColumns = ["noun_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["noun_id"])
    ]
)
data class NounDeclensionsForm(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val nominativ: String? = null,
    val genitiv: String? = null,
    val dativ: String? = null,
    val akkusativ: String? = null,

    @ColumnInfo(name = "plural_nominativ")
    val pluralNominativ: String? = null,

    @ColumnInfo(name = "plural_genitiv")
    val pluralGenitiv: String? = null,

    @ColumnInfo(name = "plural_dativ")
    val pluralDativ: String? = null,

    @ColumnInfo(name = "plural_akkusativ")
    val pluralAkkusativ: String? = null,

    @ColumnInfo(name = "noun_id")
    val nounId: Long
)
