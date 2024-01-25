package com.example.enguzdictionary.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.enguzdictionary.data.models.WordData

@Dao
interface WordDao {
    @Query("Select * From dictionary")
    fun getWord() : Cursor

    @Query("SELECT * FROM dictionary WHERE english LIKE :text || '%'")
    fun getMatchingWordsByEnglish(text: String): Cursor

    @Query("SELECT * FROM dictionary WHERE uzbek LIKE :text || '%'")
    fun getMatchingWordsByUzbek(text: String): Cursor


    @Query("Select * From dictionary where is_favourite = 1")
    fun getWordsByfavourite(): List<WordData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(wordData: WordData)

    @Query("Select * From dictionary where is_favouriteUz = 1")
    fun getUzbekSavedWords(): List<WordData>
}