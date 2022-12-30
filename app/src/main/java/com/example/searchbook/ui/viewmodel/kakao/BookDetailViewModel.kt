package com.example.searchbook.ui.viewmodel.kakao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbook.data.model.Book
import com.example.searchbook.repository.BookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository
) : ViewModel() {

    fun saveBook(book : Book){
        viewModelScope.launch(Dispatchers.IO){
            bookSearchRepository.insertBooks(book)
        }
    }
}