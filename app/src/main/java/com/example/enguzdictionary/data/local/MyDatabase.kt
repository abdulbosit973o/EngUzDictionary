package com.example.enguzdictionary.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.enguzdictionary.data.dao.WordDao
import com.example.enguzdictionary.data.models.WordData
@Database(version = 1, entities = [WordData::class])
abstract class MyDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDao

    companion object {
        @Volatile
        private lateinit var instance: MyDatabase

        fun init(context: Context) {
            if (!(Companion::instance.isInitialized)) {
                synchronized(lock = "") {
                    instance = Room.databaseBuilder(context, MyDatabase::class.java, "dictionary.db")
                        .createFromAsset("dictionary.db")
                        .build()
                }
            }
        }
        fun getInstance() = instance
    }
}
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        val query = "ALTER TABLE groupTable ADD COLUMN description TEXT"
        db.execSQL(query)
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        val query = "ALTER TABLE groupTable ADD COLUMN description TEXT"
        db.execSQL(query)
    }
}
