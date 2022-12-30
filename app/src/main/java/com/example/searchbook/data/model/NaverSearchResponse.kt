package com.example.searchbook.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NaverSearchResponse(

    @Json(name = "display")
    val display: Int,   // 한 번에 표시할 검색 결과 개수

    @Json(name = "items")
    val items: List<NaverBook>,
    @Json(name = "lastBuildDate")   // 검색 결과를 생성한 시간

    val lastBuildDate: String,
    @Json(name = "start")
    val start: Int,     // 검색 시작 위치

    @Json(name = "total")
    val total: Int      // 총 검색 결과 개수
)