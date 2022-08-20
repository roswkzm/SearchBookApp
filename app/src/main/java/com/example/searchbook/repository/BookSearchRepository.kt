package com.example.searchbook.repository

import com.example.searchbook.data.model.Book
import com.example.searchbook.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BookSearchRepository {

    suspend fun SearchBooks(
        query : String,
        sort : String,
        page : Int,
        size : Int
    ) : Response<SearchResponse>

    suspend fun insertBooks(book : Book)

    suspend fun deleteBooks(book : Book)

    fun getFavoriteBooks() : Flow<List<Book>>

}