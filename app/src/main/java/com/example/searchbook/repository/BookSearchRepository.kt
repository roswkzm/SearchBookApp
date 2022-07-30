package com.example.searchbook.repository

import com.example.searchbook.data.model.SearchResponse
import retrofit2.Response

interface BookSearchRepository {

    suspend fun SearchBooks(
        query : String,
        sort : String,
        page : Int,
        size : Int
    ) : Response<SearchResponse>
}