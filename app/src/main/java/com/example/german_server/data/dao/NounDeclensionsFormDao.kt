package com.example.german_server.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.german_server.data.entities.NounDeclensionsForm

@Dao
interface NounDeclensionsFormDao {
    @Insert
    suspend fun insert(form: NounDeclensionsForm)
    @Query("SELECT * FROM words_noundeclensionsform ORDER BY RANDOM() LIMIT :count")
    fun getRandomNounDeclensionsForm(count: Int): List<NounDeclensionsForm>
    @Insert
    suspend fun insertAll(forms: List<NounDeclensionsForm>)

    @Query("SELECT * FROM words_noundeclensionsform")
    suspend fun getAll(): List<NounDeclensionsForm>

    @Query("SELECT * FROM words_noundeclensionsform WHERE id = :id")
    suspend fun getById(id: Long): NounDeclensionsForm?
}

