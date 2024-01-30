package com.example.enguzdictionary.presentation.bookmarks

import com.example.enguzdictionary.data.models.WordData

interface SavedContract {
    interface Model{
        fun getCursorFromRepo(): List<WordData>
        fun getUzListFromRepo(): List<WordData>
        fun updateData(wordData: WordData)
    }
    interface View {
        fun Cursor(cursor: List<WordData>)
    }
    interface Presenter  {
        fun loadCursor()
        fun loadUzList()
        fun updateSaved(wordData: WordData)
    }
}
