package com.example.searchbook.util

import com.example.searchbook.BuildConfig

object Constants {
    const val BASE_URL = "https://dapi.kakao.com/"
    const val API_KEY = BuildConfig.bookApiKey
    const val SEARCH_BOOK_TIME_DELAY = 300L
    const val DATASTORE_NAME = "preferences_datastore"
    const val PAGING_SIZE = 15

    const val NAVER_BASE_URL = "https://openapi.naver.com/v1/search/"
    const val NAVER_CLIENT_ID = BuildConfig.naverClientId
    const val NAVER_CLIENT_SECRET = BuildConfig.naverClientSecret
}

enum class Sort(val value : String){
    ACCURACY("accuracy"),
    LATEST("latest")
}

enum class NaverSort(val value : String) {
    SIM("sim"),
    DATE("date")
}