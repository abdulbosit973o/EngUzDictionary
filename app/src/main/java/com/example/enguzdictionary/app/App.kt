package com.example.enguzdictionary.app

import android.app.Application
import com.example.enguzdictionary.data.local.MyDatabase
import com.example.enguzdictionary.data.local.MySharedPref

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
        MySharedPref.getInstance(this)
    }
}