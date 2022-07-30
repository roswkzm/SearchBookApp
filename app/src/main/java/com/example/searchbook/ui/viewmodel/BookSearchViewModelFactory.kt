package com.example.searchbook.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.searchbook.repository.BookSearchRepository
import java.lang.IllegalArgumentException

class BookSearchViewModelFactory(
    private val bookSearchRepository: BookSearchRepository
) :ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookSearchViewModel::class.java)){
            return BookSearchViewModel(bookSearchRepository) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }
}