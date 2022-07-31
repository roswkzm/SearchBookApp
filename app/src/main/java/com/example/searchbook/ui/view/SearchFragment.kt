package com.example.searchbook.ui.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchbook.R
import com.example.searchbook.data.model.Book
import com.example.searchbook.databinding.FragmentSearchBinding
import com.example.searchbook.ui.adapter.BookPreviewAdapter
import com.example.searchbook.ui.viewmodel.BookSearchViewModel
import com.example.searchbook.util.Constants.SEARCH_BOOK_TIME_DELAY

class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookSearchViewModel: BookSearchViewModel
    private lateinit var bookPreviewAdapter : BookPreviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookSearchViewModel = (activity as MainActivity).viewModel

        initUI()
        searchBooks()
        observeViewModel()
    }

    private fun initUI(){
        bookPreviewAdapter = BookPreviewAdapter()
        binding.rvSearchBooks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = bookPreviewAdapter
        }
    }

    private fun observeViewModel(){
        bookSearchViewModel.searchResult.observe(viewLifecycleOwner, Observer { searchResponse ->
            val books : List<Book> = searchResponse.documents
            bookPreviewAdapter.submitList(books)
        })
    }

    private fun searchBooks(){
        var startTime : Long = System.currentTimeMillis()
        var endtime : Long

        binding.etSearch.text =
            Editable.Factory.getInstance().newEditable(bookSearchViewModel.query)

        binding.etSearch.addTextChangedListener{ text : Editable? ->
            endtime = System.currentTimeMillis()
            if (endtime - startTime >= SEARCH_BOOK_TIME_DELAY){
                text.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()){
                        bookSearchViewModel.searchBooks(query)
                        bookSearchViewModel.query = query
                    }
                }
            }
            startTime = endtime
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}