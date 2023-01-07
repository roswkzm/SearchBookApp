package com.example.searchbook.ui.viewmodel.naver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.searchbook.data.model.Book
import com.example.searchbook.data.model.NaverBook
import com.example.searchbook.data.model.NaverSearchResponse
import com.example.searchbook.repository.naver.NaverBookSearchRepository
import com.example.searchbook.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverSearchViewModel @Inject constructor(
    private val bookSearchRepository: NaverBookSearchRepository
) : ViewModel() {

    private var _searchBookResult: MutableStateFlow<UiState<NaverSearchResponse>> =
        MutableStateFlow(UiState.Loading())
    var searchBookResult: StateFlow<UiState<NaverSearchResponse>> = _searchBookResult

    private val _searchPagingResult = MutableStateFlow<PagingData<NaverBook>>(PagingData.empty())
    val searchPagingResult : StateFlow<PagingData<NaverBook>> = _searchPagingResult.asStateFlow()

    fun searchBooks(query : String) {
        viewModelScope.launch {
            bookSearchRepository.searchNaverBooks(query, 10, 1, bookSearchRepository.getSortMode().first())
                .catch { error ->
                    _searchBookResult.value = UiState.Error(error.message.toString())
                }
                .collect { value ->
                    _searchBookResult.value = value
                }

        }

    }

    fun searchBooksPaging(query: String) {
        viewModelScope.launch {
            bookSearchRepository.getSearchBookPaging(query, bookSearchRepository.getSortMode().first())
                .cachedIn(viewModelScope)
                .collect{
                    _searchPagingResult.value = it
                }
        }
    }
}