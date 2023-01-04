package com.example.searchbook.repository.naver

import com.example.searchbook.data.model.NaverBook
import com.example.searchbook.data.model.NaverSearchResponse
import com.example.searchbook.util.UiState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NaverBookSearchRepository {

    suspend fun SearchNaverBooks(
        query : String,
        display : Int,
        start : Int,
        sort : String
    ) : Flow<UiState<NaverSearchResponse>>

    suspend fun insertBook(book : NaverBook)

    suspend fun deleteBook(book: NaverBook)

    fun getFavoriteBooks() : Flow<List<NaverBook>>
}