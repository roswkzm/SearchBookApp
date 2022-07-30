package com.example.searchbook.repository

import com.example.searchbook.data.model.SearchResponse
import com.example.searchbook.network.RetrofitInstance.api
import retrofit2.Response

class BookSearchRepositoryImpl : BookSearchRepository {

    override suspend fun SearchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Response<SearchResponse> {
        return api.searchBook(query, sort, page, size)
    }
}