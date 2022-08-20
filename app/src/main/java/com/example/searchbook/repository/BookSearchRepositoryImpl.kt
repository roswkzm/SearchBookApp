package com.example.searchbook.repository

import androidx.lifecycle.LiveData
import com.example.searchbook.data.db.AppDatabase
import com.example.searchbook.data.model.Book
import com.example.searchbook.data.model.SearchResponse
import com.example.searchbook.network.RetrofitInstance.api
import retrofit2.Response

class BookSearchRepositoryImpl(
    private val db : AppDatabase
) : BookSearchRepository {

    override suspend fun SearchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Response<SearchResponse> {
        return api.searchBook(query, sort, page, size)
    }

    override suspend fun insertBooks(book: Book) {
        db.bookSearchDao().insertBook(book)
    }

    override suspend fun deleteBooks(book: Book) {
        db.bookSearchDao().deleteBook(book)
    }

    override fun getFavoriteBooks(): LiveData<List<Book>> {
        return db.bookSearchDao().getFavoriteBooks()
    }
}