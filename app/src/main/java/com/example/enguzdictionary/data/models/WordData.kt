package com.example.enguzdictionary.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "dictionary")
data class WordData(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @NonNull
    val english: String,
    @NonNull
    val type: String,
    @NonNull
    val transcript: String,
    @NonNull
    val uzbek: String,
    @NonNull
    val countable: String,
    @NonNull
    var is_favourite: Int,
    @ColumnInfo(name = "is_favouriteUz")
    var is_favouriteUz: Int? = null
)
