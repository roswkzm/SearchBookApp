package com.example.searchbook.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchbook.data.db.AppDatabase
import com.example.searchbook.data.model.Book
import com.example.searchbook.data.model.SearchResponse
import com.example.searchbook.network.RetrofitInstance.api
import com.example.searchbook.repository.BookSearchRepositoryImpl.PreferencesKeys.SORT_MODE
import com.example.searchbook.util.Constants.PAGING_SIZE
import com.example.searchbook.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException

class BookSearchRepositoryImpl(
    private val db : AppDatabase,
    private val dataStore : DataStore<Preferences>
) : BookSearchRepository {

    override suspend fun SearchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Response<SearchResponse> {
        return api.searchBook(query, sort, page, size)
    }

    override suspend fun insertBooks(book: Book) {
        db.bookSearchDao().insertBook(book)
    }

    override suspend fun deleteBooks(book: Book) {
        db.bookSearchDao().deleteBook(book)
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return db.bookSearchDao().getFavoriteBooks()
    }

    // DateStore
    private object PreferencesKeys{
        val SORT_MODE = stringPreferencesKey("sort_mode")
    }

    override suspend fun saveSortMode(mode: String) {
        dataStore.edit { perfs ->
            perfs[SORT_MODE] = mode
        }
    }

    override suspend fun getSortMode(): Flow<String> {
        return dataStore.data.catch { exception ->
            if (exception is IOException){
                exception.printStackTrace()
                emit(emptyPreferences())
            } else{
                throw exception
            }
        }
            .map { perfs ->
                perfs[SORT_MODE] ?: Sort.ACCURACY.value
            }
    }

    // Paging
    override fun getFavoritePagingBooks(): Flow<PagingData<Book>> {
        val pagingSourceFactory = {db.bookSearchDao().getFavoritePagingBooks()}
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