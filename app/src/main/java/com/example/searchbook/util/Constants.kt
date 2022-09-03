package com.example.searchbook.util

import com.example.searchbook.BuildConfig

object Constants {
    const val BASE_URL = "https://dapi.kakao.com/"
    const val API_KEY = BuildConfig.bookApiKey
    const val SEARCH_BOOK_TIME_DELAY = 100L
    const val DATASTORE_NAME = "preferences_datastore"
}

enum class Sort(val value : String){
    ACCURACY("accuracy"),
    LATEST("latest")
}