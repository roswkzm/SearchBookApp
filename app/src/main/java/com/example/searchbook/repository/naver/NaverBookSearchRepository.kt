package com.example.searchbook.repository.naver

import com.example.searchbook.data.model.NaverSearchResponse
import retrofit2.Response

interface NaverBookSearchRepository {

    suspend fun SearchNaverBooks(
        query : String,
        display : Int,
        start : Int,
        sort : String
    ) : Response<NaverSearchResponse>
}