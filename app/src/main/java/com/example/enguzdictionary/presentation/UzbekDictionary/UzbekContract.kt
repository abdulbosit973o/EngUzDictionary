package com.example.enguzdictionary.presentation.UzbekDictionary

import android.database.Cursor
import com.example.enguzdictionary.data.models.WordData

interface UzbekContract {
    interface Model{
        fun getCursorFromRepo(): Cursor
        fun getCursorByLikeFromRepo(string: String): Cursor
        fun updateData(wordData: WordData)


    }
    interface View {
        fun Cursor(cursor: Cursor)
    }
    interface Presenter{
        fun loadCursor()
        fun loadCursorByQuery(query:String)
        fun updateData(it: WordData)
    }
}