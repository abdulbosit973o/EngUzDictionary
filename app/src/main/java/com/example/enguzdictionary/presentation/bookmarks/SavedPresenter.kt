package com.example.enguzdictionary.presentation.bookmarks

import com.example.enguzdictionary.data.models.WordData
import java.util.concurrent.Executors

class SavedPresenter(var view: SavedContract.View) : SavedContract.Presenter {
    private val model: SavedContract.Model = SavedModel()
    private val executors = Executors.newSingleThreadExecutor()


    override fun loadCursor() {
        executors.execute  {
            view.Cursor(model.getCursorFromRepo())
        }

    }

    override fun loadUzList() {
        executors.execute  {
            view.Cursor(model.getUzListFromRepo())
        }
    }

    override fun updateSaved(wordData: WordData) {
        executors.execute  {
            model.updateData(wordData)
        }
    }
}