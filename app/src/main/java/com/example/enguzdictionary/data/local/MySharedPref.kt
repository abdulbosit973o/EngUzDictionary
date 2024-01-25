package com.example.enguzdictionary.data.local

import android.content.Context
import android.content.SharedPreferences

object MySharedPref {
    private lateinit var sharedPref: SharedPreferences

    fun getInstance(context: Context): MySharedPref {
        if (!MySharedPref::sharedPref.isInitialized) {
            sharedPref = context.getSharedPreferences("fourPics", Context.MODE_PRIVATE)
        }
        return this
    }

    fun saveOpenScreen(int: Int) {
        sharedPref.edit().putInt("Open", int).apply()
    }
    fun getOpenScreen(): Int {
        return sharedPref.getInt("Open", 0)
    }

}