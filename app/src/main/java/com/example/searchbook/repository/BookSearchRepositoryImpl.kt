package com.example.searchbook.repository

import com.example.searchbook.data.db.AppDatabase
import com.example.searchbook.data.model.Book
import com.example.searchbook.data.model.SearchResponse
import com.example.searchbook.network.RetrofitInstance.api
import kotlinx.coroutines.flow.Flow
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

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return db.bookSearchDao().getFavoriteBooks()
    }
}