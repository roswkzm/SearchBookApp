package com.example.searchbook.ui.viewmodel.naver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbook.data.model.NaverSearchResponse
import com.example.searchbook.repository.naver.NaverBookSearchRepository
import com.example.searchbook.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverSearchViewModel @Inject constructor(
    private val bookSearchRepository: NaverBookSearchRepository
) : ViewModel() {

    var _searchBookResult: MutableStateFlow<UiState<NaverSearchResponse>> =
        MutableStateFlow(UiState.Loading())
    var searchBookResult: StateFlow<UiState<NaverSearchResponse>> = _searchBookResult

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
}