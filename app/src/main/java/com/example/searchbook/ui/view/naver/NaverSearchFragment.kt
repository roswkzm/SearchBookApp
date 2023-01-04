package com.example.searchbook.ui.view.naver

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchbook.R
import com.example.searchbook.databinding.FragmentNaverSearchBinding
import com.example.searchbook.ui.adapter.naver.NaverBookPreviewAdapter
import com.example.searchbook.ui.viewmodel.naver.NaverSearchViewModel
import com.example.searchbook.util.Constants.SEARCH_BOOK_TIME_DELAY
import com.example.searchbook.util.UiState
import com.example.searchbook.util.collectLatestStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NaverSearchFragment : Fragment() {

    private var _binding : FragmentNaverSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookAdapter : NaverBookPreviewAdapter

    private val viewModel by viewModels<NaverSearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_naver_search, container, false)

        initUi()
        searchBooks()
        subscribeUi()

        return binding.root
    }

    private fun initUi(){
        bookAdapter = NaverBookPreviewAdapter()
        binding.rvSearchBooks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = bookAdapter
        }

        bookAdapter.setOnItemClickListener {
            val action = NaverSearchFragmentDirections.actionFragmentNaverSearchToBookDetailFragment(null, it)
            findNavController().navigate(action)
        }
    }

    private fun subscribeUi() {
        collectLatestStateFlow(viewModel.searchBookResult){
            when(it) {
                is UiState.Success -> {
                    bookAdapter.submitList(it.data?.items)
                    binding.progressBar.isVisible = false
                }
                is UiState.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
                is UiState.Loading -> {
                    binding.progressBar.isVisible = !binding.etSearch.text.isNullOrEmpty()
                }
            }
        }
    }

    private fun searchBooks(){
        var startTime : Long = System.currentTimeMillis()
        var endTime : Long

        binding.etSearch.addTextChangedListener{ text : Editable? ->
            endTime = System.currentTimeMillis()
            if (endTime - startTime >= SEARCH_BOOK_TIME_DELAY){
                text.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()){
                        viewModel.searchBooks(query)
                    }
                }
            }
            startTime = endTime
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}