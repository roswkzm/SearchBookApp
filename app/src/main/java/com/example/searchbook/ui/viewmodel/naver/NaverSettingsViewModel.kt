package com.example.searchbook.ui.viewmodel.naver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbook.repository.naver.NaverBookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NaverSettingsViewModel @Inject constructor(
    private val bookSearchRepository: NaverBookSearchRepository
) : ViewModel(){

    fun setSortMode(value : String){
        viewModelScope.launch(Dispatchers.IO){
            bookSearchRepository.setSortMode(value)
        }
    }

    suspend fun getSortMode() = withContext(Dispatchers.IO){
        bookSearchRepository.getSortMode().first()
    }
}