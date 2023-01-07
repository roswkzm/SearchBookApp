package com.example.searchbook.repository.naver

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchbook.data.db.AppDatabase
import com.example.searchbook.data.model.NaverBook
import com.example.searchbook.data.model.NaverSearchResponse
import com.example.searchbook.network.NaverBookSearchService
import com.example.searchbook.repository.naver.NaverBookSearchRepositoryImpl.PreferencesKeys.NAVER_SORT_MODE
import com.example.searchbook.util.Constants.PAGING_SIZE
import com.example.searchbook.util.NaverSort
import com.example.searchbook.util.UiState
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NaverBookSearchRepositoryImpl @Inject constructor(
    private val api : NaverBookSearchService,
    private val db : AppDatabase,
    private val dataStore : DataStore<Preferences>
) : NaverBookSearchRepository {

    override suspend fun searchNaverBooks(
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

    override suspend fun insertBook(book: NaverBook) {
        db.naverBookSearchDao().insertBook(book)
    }

    override suspend fun deleteBook(book: NaverBook) {
        db.naverBookSearchDao().deleteBook(book)
    }

    override fun getFavoriteBooks(): Flow<List<NaverBook>> {
        return db.naverBookSearchDao().getFavoriteBooks()
    }

    private object PreferencesKeys{
        val NAVER_SORT_MODE = stringPreferencesKey("naver_sort_mode")
    }

    override suspend fun setSortMode(value: String) {
        dataStore.edit { perfs ->
            perfs[NAVER_SORT_MODE] = value
        }
    }

    override suspend fun getSortMode(): Flow<String> {
        return dataStore.data.catch { exception ->
            if (exception is IOException){
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        } .map { perfs ->
            perfs[NAVER_SORT_MODE] ?: NaverSort.SIM.value
        }
    }

    override fun getFavoritePagingBooks(): Flow<PagingData<NaverBook>> {
        val pagingSourceFactory = {db.naverBookSearchDao().getFavoritePagingBooks()}
        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false,
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override fun getSearchBookPaging(query: String, sort: String): Flow<PagingData<NaverBook>> {
        val pagingSourceFactory = {NaverBookSearchPagingSource(api, query, sort)}

        return Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false,
                maxSize = PAGING_SIZE * 3
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}