package com.example.enguzdictionary.presentation.UzbekDictionary

import com.example.enguzdictionary.data.models.WordData
import com.example.enguzdictionary.presentation.home.HomeContract
import java.util.concurrent.Executors

class UzbekPresenter(var view: UzbekContract.View) : UzbekContract.Presenter {
    private val model: UzbekContract.Model = UzbekModel()
    private val executors = Executors.newSingleThreadExecutor()


    override fun loadCursor() {
        executors.execute {
            val cursor = model.getCursorFromRepo()
            view.Cursor(cursor)
        }

    }

    override fun loadCursorByQuery(query: String) {
        executors.execute {
            val cursor = model.getCursorByLikeFromRepo(query)
            view.Cursor(cursor)
        }
    }

    override fun updateData(it: WordData) {
        executors.execute {
            model.updateData(it)
            loadCursor()
        }

    }
}