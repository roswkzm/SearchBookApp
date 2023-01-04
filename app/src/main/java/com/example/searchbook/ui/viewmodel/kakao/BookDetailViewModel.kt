package com.example.searchbook.ui.viewmodel.kakao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbook.data.model.Book
import com.example.searchbook.data.model.NaverBook
import com.example.searchbook.repository.kakao.BookSearchRepository
import com.example.searchbook.repository.naver.NaverBookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
    private val naverBookSearchRepository: NaverBookSearchRepository
) : ViewModel() {

    fun saveBook(book : Book){
        viewModelScope.launch(Dispatchers.IO){
            bookSearchRepository.insertBooks(book)
        }
    }

    fun saveNaverBook(book: NaverBook){
        viewModelScope.launch(Dispatchers.IO){
            naverBookSearchRepository.insertBook(book)
        }
    }
}