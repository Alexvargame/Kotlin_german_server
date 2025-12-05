package com.example.german.data.entities

import androidx.room.*
import java.util.*

@Entity(
    tableName = "callback_sitemessage",
    foreignKeys = [
        ForeignKey(
            entity = BaseUser::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BaseUser::class,
            parentColumns = ["id"],
            childColumns = ["recipient_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["recipient_id"])
    ]
)
data class CallbackSiteMessage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val text: String,

    val is_answered: Boolean,

    val answer_text: String? = null,

    val created_at: Long,          // UNIX timestamp
    val answered_at: Long? = null, // UNIX timestamp

    @ColumnInfo(name = "user_id")
    val userId: Long,

    val telegram_id: Int? = null,

    @ColumnInfo(name = "recipient_id")
    val recipientId: Long? = null
)
