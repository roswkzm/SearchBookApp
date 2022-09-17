package com.example.searchbook.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import androidx.work.WorkManager
import com.example.searchbook.repository.BookSearchRepository
import java.lang.IllegalArgumentException

class BookSearchViewModelFactory(
    private val bookSearchRepository: BookSearchRepository,
    private val workManager: WorkManager,
    owner : SavedStateRegistryOwner,
    defaultArgs : Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs){
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(BookSearchViewModel::class.java)){
            return BookSearchViewModel(bookSearchRepository, handle, workManager) as T
        }
        throw IllegalArgumentException("ViewModel Class not found")
    }

}