package com.example.searchbook.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.searchbook.R
import com.example.searchbook.data.db.AppDatabase
import com.example.searchbook.databinding.ActivityMainBinding
import com.example.searchbook.repository.BookSearchRepositoryImpl
import com.example.searchbook.ui.viewmodel.BookSearchViewModel
import com.example.searchbook.ui.viewmodel.BookSearchViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var viewModel : BookSearchViewModel
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpJetpackNavigation()

        val database = AppDatabase.getInstance(this)
        val bookSearchRepository = BookSearchRepositoryImpl(database)
        val factory = BookSearchViewModelFactory(bookSearchRepository, this)
        viewModel = ViewModelProvider(this, factory)[BookSearchViewModel::class.java]

        setContentView(binding.root)
    }

    private fun setUpJetpackNavigation(){
        val host = supportFragmentManager
            .findFragmentById(R.id.booksearch_nav_host_fragment) as NavHostFragment ?: return
        navController = host.navController
        binding.bottomNavView.setupWithNavController(navController)
    }
}