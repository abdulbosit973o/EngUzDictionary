package com.example.enguzdictionary.presentation.bookmarks

import android.database.Cursor
import com.example.enguzdictionary.data.models.WordData

interface SavedContract {
    interface Model{
        fun getCursorFromRepo(): List<WordData>
        fun getUzListFromRepo(): List<WordData>
    }
    interface View {
        fun Cursor(cursor: List<WordData>)
    }
    interface Presenter  {
        fun loadCursor()
        fun loadUzList()
    }
}
