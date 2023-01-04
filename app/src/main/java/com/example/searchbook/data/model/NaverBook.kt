package com.example.searchbook.data.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NaverBook(

    @Json(name = "author")
    val author: String,     // 저자 이름

    @Json(name = "description")
    val description: String,        // 네이버 도서의 책 소개

    @Json(name = "discount")
    val discount: String?,       // 판매 가격. 절판 등의 이유로 가격이 없으면 값을 반환하지 않습니다.

    @Json(name = "image")
    val image: String,      // 섬네일 이미지의 URL

    @Json(name = "isbn")
    val isbn: String,       // 국제 표준 도서번호

    @Json(name = "link")
    val link: String,       // 네이버 도서 정보 URL

    @Json(name = "pubdate")
    val pubDate: String,        // 출간일

    @Json(name = "publisher")
    val publisher: String,      // 출판사

    @Json(name = "title")
    val title: String       // 책 제목
) : Parcelable