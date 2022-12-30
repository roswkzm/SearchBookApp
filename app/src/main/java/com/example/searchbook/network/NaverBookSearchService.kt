package com.example.searchbook.network

import com.example.searchbook.data.model.NaverSearchResponse
import com.example.searchbook.data.model.SearchResponse
import com.example.searchbook.util.Constants.NAVER_CLIENT_ID
import com.example.searchbook.util.Constants.NAVER_CLIENT_SECRET
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverBookSearchService {

    @Headers("X-Naver-Client-Id: $NAVER_CLIENT_ID",
    "X-Naver-Client-Secret: $NAVER_CLIENT_SECRET")
    @GET("book.json")
    suspend fun searchBook(
        @Query("query") query : String,
        @Query("display") display : Int,
        @Query("start") start : Int,
        @Query("sort") sort : String
    ): Response<NaverSearchResponse>
}