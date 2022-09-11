package com.example.searchbook.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.searchbook.data.model.Book
import com.example.searchbook.data.model.SearchResponse
import com.example.searchbook.repository.BookSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookSearchViewModel(
    private val bookSearchRepository: BookSearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult : LiveData<SearchResponse> get() = _searchResult

    fun searchBooks(query : String){
        viewModelScope.launch(Dispatchers.IO){
            val response = bookSearchRepository.SearchBooks(query, getSortMode(), 1, 15)
            if (response.isSuccessful){
                response.body()?.let { body ->
                    _searchResult.postValue(body)
                }
            }
        }
    }

    fun saveBook(book : Book){
        viewModelScope.launch(Dispatchers.IO){
            bookSearchRepository.insertBooks(book)
        }
    }

    fun deleteBook(book : Book){
        viewModelScope.launch(Dispatchers.IO){
            bookSearchRepository.deleteBooks(book)
        }
    }

    val favoriteBooks : StateFlow<List<Book>> = bookSearchRepository.getFavoriteBooks()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    var query = String()
        set(value) {
            field = value
            savedStateHandle.set(SAVE_STATE_KEY, value)
        }

    init {
        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
    }

    // DataStore
    fun saveSortMode(value : String) {
        viewModelScope.launch(Dispatchers.IO){
            bookSearchRepository.saveSortMode(value)
        }
    }

    suspend fun getSortMode() = withContext(Dispatchers.IO){
            bookSearchRepository.getSortMode().first()
        }

    val favoritePagingBooks : StateFlow<PagingData<Book>> =
        bookSearchRepository.getFavoritePagingBooks().cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    // Paging - Search
    private val _searchPagingResult = MutableStateFlow<PagingData<Book>>(PagingData.empty())
    val searchPagingResult : StateFlow<PagingData<Book>> = _searchPagingResult.asStateFlow()

    fun searchBooksPaging(query: String){
        viewModelScope.launch {
            bookSearchRepository.searchBooksPaging(query, getSortMode())
                .cachedIn(viewModelScope)
                .collect{
                    _searchPagingResult.value = it
                }

        }
    }

    companion object{
        private const val SAVE_STATE_KEY = "query"
    }
}