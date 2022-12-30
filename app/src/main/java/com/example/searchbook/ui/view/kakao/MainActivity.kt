package com.example.searchbook.ui.view.kakao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.searchbook.R
import com.example.searchbook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.searchKakao.setOnClickListener {
            val intent = Intent(this, KakaoActivity::class.java)
            startActivity(intent)
        }

        binding.searchNaver.setOnClickListener {

        }
    }
}