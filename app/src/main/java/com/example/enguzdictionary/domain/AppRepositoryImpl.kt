package com.example.enguzdictionary.domain

import android.database.Cursor
import android.util.Log
import com.example.enguzdictionary.data.local.MyDatabase
import com.example.enguzdictionary.data.models.WordData

class AppRepositoryImpl : AppRepository {
    companion object {
        private lateinit var instance: AppRepository

        fun getInstance(): AppRepository {
            if (!(Companion::instance.isInitialized)) instance = AppRepositoryImpl()
            return instance
        }
    }

    private val db = MyDatabase.getInstance()
    private val wordDao = db.getWordDao()

    override fun cursor(): Cursor {
        Log.d("TTT", "count of word -> ${wordDao.getWord().count}")
        return wordDao.getWord()
    }

    override fun cursorByFavouriteUzbek(): List<WordData> {
        return wordDao.getUzbekSavedWords()
    }

    override fun cursorByLike(query: String): Cursor {
        return wordDao.getMatchingWordsByEnglish(query)
    }

    override fun cursorByLikeUzbek(query: String): Cursor {
        return wordDao.getMatchingWordsByUzbek(query)
    }

    override fun cursorByFavourite(): List<WordData> {
        return wordDao.getWordsByfavourite()
    }

    override fun updateData(wordData: WordData) {
        Log.d("TTT", "${wordData.english} ${wordData.is_favourite}")
        wordDao.updateData(wordData)
    }
}