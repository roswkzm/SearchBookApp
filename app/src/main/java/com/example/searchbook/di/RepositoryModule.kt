package com.example.searchbook.di

import com.example.searchbook.repository.BookSearchRepository
import com.example.searchbook.repository.BookSearchRepositoryImpl
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
}