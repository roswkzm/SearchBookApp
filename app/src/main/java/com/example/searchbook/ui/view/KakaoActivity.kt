package com.example.searchbook.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.searchbook.R
import com.example.searchbook.databinding.ActivityKakaoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KakaoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityKakaoBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kakao)

        setUpJetpackNavigation()
    }

    private fun setUpJetpackNavigation(){
        val host = supportFragmentManager
            .findFragmentById(R.id.booksearch_nav_host_fragment) as NavHostFragment ?: return
        navController = host.navController
        binding.bottomNavView.setupWithNavController(navController)
    }
}