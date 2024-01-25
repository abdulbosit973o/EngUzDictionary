package com.example.enguzdictionary.presentation.home

import android.database.Cursor
import com.example.enguzdictionary.data.models.WordData
import com.example.enguzdictionary.domain.AppRepository
import com.example.enguzdictionary.domain.AppRepositoryImpl

class HomeModel : HomeContract.Model {
    private  val  repo: AppRepository = AppRepositoryImpl.getInstance()
    override fun getCursorFromRepo(): Cursor {
        return repo.cursor()
    }


    override fun getCursorByLikeFromRepo(query: String): Cursor {
        return  repo.cursorByLike(query)
    }

    override fun updateData(wordData: WordData) {
        repo.updateData(wordData)
    }
}