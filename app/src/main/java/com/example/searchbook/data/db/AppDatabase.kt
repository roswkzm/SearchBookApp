package com.example.searchbook.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.searchbook.data.model.Book

@Database(
    entities = [
        Book::class
    ],
    version = 1
)
@TypeConverters(OrmConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookSearchDao() : BookSearchDao
}