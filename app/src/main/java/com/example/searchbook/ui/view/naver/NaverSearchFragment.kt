package com.example.searchbook.ui.view.naver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.searchbook.R
import com.example.searchbook.databinding.FragmentNaverSearchBinding
import com.example.searchbook.ui.viewmodel.naver.NaverSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaverSearchFragment : Fragment() {

    private var _binding : FragmentNaverSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<NaverSearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_naver_search, container, false)

        viewModel.searchBooks()

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}