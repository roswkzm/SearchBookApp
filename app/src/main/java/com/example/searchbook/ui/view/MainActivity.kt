package com.example.searchbook.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.searchbook.R
import com.example.searchbook.databinding.ActivityMainBinding
import com.example.searchbook.repository.BookSearchRepositoryImpl
import com.example.searchbook.ui.viewmodel.BookSearchViewModel
import com.example.searchbook.ui.viewmodel.BookSearchViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var viewModel : BookSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setBottomNavigationView()
        binding.bottomNavView.selectedItemId = R.id.fragment_search

        val bookSearchRepository = BookSearchRepositoryImpl()
        val factory = BookSearchViewModelFactory(bookSearchRepository)
        viewModel = ViewModelProvider(this, factory)[BookSearchViewModel::class.java]

        viewModel.searchBooks("android")

        viewModel.searchResult.observe(this, Observer { data ->
            Log.d("ㅎㅇㅎㅇ", data.toString())
        })
        setContentView(binding.root)
    }

    private fun setBottomNavigationView(){
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.fragment_search -> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.fragment_favorite -> {
                    replaceFragment(FavoriteFragment())
                    true
                }
                R.id.fragment_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }
}