package com.example.searchbook.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.searchbook.R
import com.example.searchbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setBottomNavigationView()
        binding.bottomNavView.selectedItemId = R.id.fragment_search

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