package com.example.searchbook.ui.viewmodel

import androidx.lifecycle.*
import com.example.searchbook.data.model.SearchResponse
import com.example.searchbook.repository.BookSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookSearchViewModel(
    private val bookSearchRepository: BookSearchRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult : LiveData<SearchResponse> get() = _searchResult

    fun searchBooks(query : String){
        viewModelScope.launch(Dispatchers.IO){
            val response = bookSearchRepository.SearchBooks(query, "accuracy", 1, 15)
            if (response.isSuccessful){
                response.body()?.let { body ->
                    _searchResult.postValue(body)
                }
            }
        }
    }

    var query = String()
        set(value) {
            field = value
            savedStateHandle.set(SAVE_STATE_KEY, value)
        }

    init {
        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
    }

    companion object{
        private const val SAVE_STATE_KEY = "query"
    }
}