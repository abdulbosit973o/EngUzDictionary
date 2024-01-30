package com.example.enguzdictionary.presentation.bookmarks

import com.example.enguzdictionary.domain.AppRepository
import com.example.enguzdictionary.data.models.WordData
import com.example.enguzdictionary.domain.AppRepositoryImpl
import java.util.concurrent.Executors

class SavedModel: SavedContract.Model {
    private  val  repo: AppRepository = AppRepositoryImpl.getInstance()
    val executors = Executors.newSingleThreadExecutor()
    override fun getCursorFromRepo(): List<WordData> {
        return repo.cursorByFavourite()
    }

    override fun getUzListFromRepo(): List<WordData> {
        return repo.cursorByFavouriteUzbek()
    }

    override fun updateData(wordData: WordData) {
        repo.updateData(wordData)
    }
}