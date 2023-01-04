package com.example.searchbook.repository.naver

import com.example.searchbook.data.model.NaverSearchResponse
import com.example.searchbook.network.NaverBookSearchService
import com.example.searchbook.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    ): Flow<UiState<NaverSearchResponse>> = flow{
        try {
            val searchBooks = api.searchBook(query, display, start, sort)
            if (searchBooks.isSuccessful){
                searchBooks.body()?.let {
                    emit(UiState.Success(it))
                }
            } else{
                try {
                    emit(UiState.Error(searchBooks.errorBody().toString()))
                } catch (e : Exception){
                    emit(UiState.Error(""))
                }
            }
        } catch (e : Exception){
            emit(UiState.Error(e.message ?: ""))
        }
    }
}