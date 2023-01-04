package com.example.searchbook.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.searchbook.data.model.Book
import com.example.searchbook.data.model.NaverBook

@Database(
    entities = [
        Book::class,
        NaverBook::class
    ],
    version = 1
)
@TypeConverters(OrmConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookSearchDao(): BookSearchDao
    abstract fun naverBookSearchDao(): NaverSearchDao
}