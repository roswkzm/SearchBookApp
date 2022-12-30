package com.example.searchbook.repository.naver

import com.example.searchbook.data.model.NaverSearchResponse
import com.example.searchbook.network.NaverBookSearchService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NaverBookSearchRepositoryImpl @Inject constructor(
    private val api : NaverBookSearchService
) : NaverBookSearchRepository {

    override suspend fun SearchNaverBooks(
        query: String,
        display: Int,
        start: Int,
        sort: String
    ): Response<NaverSearchResponse> {
        return api.searchBook(query, display, start, sort)
    }
}