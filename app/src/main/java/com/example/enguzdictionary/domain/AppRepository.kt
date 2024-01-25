package com.example.enguzdictionary.domain

import android.database.Cursor
import com.example.enguzdictionary.data.models.WordData

interface AppRepository {
    fun cursor(): Cursor
    fun cursorByLike(query: String): Cursor
    fun cursorByFavourite(): List<WordData>
    fun updateData(wordData: WordData)
    fun cursorByFavouriteUzbek(): List<WordData>
    fun cursorByLikeUzbek(query: String): Cursor

}
