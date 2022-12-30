package com.example.searchbook.ui.view.naver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.searchbook.R
import com.example.searchbook.databinding.ActivityNaverBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaverActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNaverBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_naver)

        setUpJetpackNavigation()
    }

    private fun setUpJetpackNavigation(){
        val host = supportFragmentManager
            .findFragmentById(R.id.naver_booksearch_nav_host_fragment) as NavHostFragment ?: return
        navController = host.navController
        binding.naverBottomNavView.setupWithNavController(navController)
    }
}