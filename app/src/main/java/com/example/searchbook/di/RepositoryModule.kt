package com.example.searchbook.di

import com.example.searchbook.repository.kakao.BookSearchRepository
import com.example.searchbook.repository.kakao.BookSearchRepositoryImpl
import com.example.searchbook.repository.naver.NaverBookSearchRepository
import com.example.searchbook.repository.naver.NaverBookSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindBookSearchRepository(
        bookSearchRepositoryImpl : BookSearchRepositoryImpl
    ) : BookSearchRepository

    @Singleton
    @Binds
    abstract fun bindNaverSearchRepository(
        naverBookSearchRepositoryImpl: NaverBookSearchRepositoryImpl
    ) : NaverBookSearchRepository
}