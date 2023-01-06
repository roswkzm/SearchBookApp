package com.example.searchbook.ui.viewmodel.naver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.searchbook.data.model.NaverBook
import com.example.searchbook.repository.naver.NaverBookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverFavoriteViewModel @Inject constructor(
    private val bookSearchRepository: NaverBookSearchRepository
) : ViewModel() {

    val favoriteBooks = bookSearchRepository.
    getFavoriteBooks().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    fun deleteBook(book : NaverBook){
        viewModelScope.launch(Dispatchers.IO){
            bookSearchRepository.deleteBook(book)
        }
    }

    val favoritePagingBooks : StateFlow<PagingData<NaverBook>> =
        bookSearchRepository.getFavoritePagingBooks()
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
}