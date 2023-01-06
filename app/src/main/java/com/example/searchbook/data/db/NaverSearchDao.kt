package com.example.searchbook.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.searchbook.data.model.NaverBook
import kotlinx.coroutines.flow.Flow

@Dao
interface NaverSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book : NaverBook)

    @Delete
    suspend fun deleteBook(book: NaverBook)

    @Query("SELECT * FROM naverBook")
    fun getFavoriteBooks() : Flow<List<NaverBook>>

    @Query("SELECT * FROM naverBook")
    fun getFavoritePagingBooks() : PagingSource<Int, NaverBook>
}