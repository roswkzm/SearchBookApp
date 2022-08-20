package com.example.searchbook.data.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "books")
data class Book(
    @field:Json(name = "authors")
    val authors: List<String>,      // 도서 저자 리스트
    @field:Json(name = "contents")
    val contents: String,       // 도서 소개
    @field:Json(name = "datetime")
    val datetime: String,       // 도서 출판날짜, ISO 8601 형식

    @PrimaryKey(autoGenerate = false)
    val isbn: String,       // 국제 표준 도서번호

    @field:Json(name = "price")
    val price: Int,     // 도서 정가
    @field:Json(name = "publisher")
    val publisher: String,      // 도서 출판사
    @field:Json(name = "sale_price")
    @ColumnInfo(name = "sale_price")
    val salePrice: Int,     // 도서 판매가
    @field:Json(name = "status")
    val status: String,     // 도서 판매 상태 정보 (정상, 품절, 절판 등)
    @field:Json(name = "thumbnail")
    val thumbnail: String,      // 	도서 표지 미리보기 URL
    @field:Json(name = "title")
    val title: String,      // 도서 제목
    @field:Json(name = "translators")
    val translators: List<String>,      // 도서 번역자 리스트
    @field:Json(name = "url")
    val url: String     // 도서 상세 URL
) : Parcelable