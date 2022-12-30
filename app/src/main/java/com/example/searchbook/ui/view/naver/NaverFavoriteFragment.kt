package com.example.searchbook.ui.view.naver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.searchbook.R
import com.example.searchbook.databinding.FragmentNaverFavoriteBinding

class NaverFavoriteFragment : Fragment() {

    private var _binding : FragmentNaverFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_naver_favorite, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}