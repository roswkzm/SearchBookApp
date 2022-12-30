package com.example.searchbook.ui.viewmodel.naver

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbook.repository.naver.NaverBookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverSearchViewModel @Inject constructor(
    private val bookSearchRepository: NaverBookSearchRepository
) : ViewModel() {

    fun searchBooks() {
        viewModelScope.launch {
            val result = bookSearchRepository.SearchNaverBooks("안드로이드", 10, 1, "sim")
            if (result.isSuccessful){
                Log.d("ㅎㅇㅎㅇ", result.body().toString())
            }
        }
    }
}