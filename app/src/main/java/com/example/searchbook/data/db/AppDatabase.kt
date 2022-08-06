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

    companion object{
        @Volatile
        private var instance : AppDatabase? = null

        private fun buildDatabase(context : Context) : AppDatabase{
            return Room.databaseBuilder(context, AppDatabase::class.java, "favorite-books")
                .build()
        }

        fun getInstance(context : Context) : AppDatabase{
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
    }
}