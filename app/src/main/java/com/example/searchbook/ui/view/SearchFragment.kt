package com.example.searchbook.ui.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchbook.R
import com.example.searchbook.data.model.Book
import com.example.searchbook.databinding.FragmentSearchBinding
import com.example.searchbook.ui.adapter.BookPreviewAdapter
import com.example.searchbook.ui.adapter.BookSearchLoadStateAdapter
import com.example.searchbook.ui.adapter.BookSearchPagingAdapter
import com.example.searchbook.ui.viewmodel.BookSearchViewModel
import com.example.searchbook.util.Constants.SEARCH_BOOK_TIME_DELAY
import com.example.searchbook.util.collectLatestStateFlow

class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookSearchViewModel: BookSearchViewModel
    private lateinit var bookSearchAdapter : BookSearchPagingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookSearchViewModel = (activity as MainActivity).viewModel

        initUI()
        searchBooks()
        setUpLoadState()
        observeViewModel()
    }

    private fun initUI(){
        bookSearchAdapter = BookSearchPagingAdapter()
        binding.rvSearchBooks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = bookSearchAdapter.withLoadStateFooter(
                footer = BookSearchLoadStateAdapter(bookSearchAdapter::retry)
            )
        }

        // BookDetailFragment로 book 정보 넘기면서 이동
        bookSearchAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToFragmentBookDetail(it)
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel(){
//        bookSearchViewModel.searchResult.observe(viewLifecycleOwner, Observer { searchResponse ->
//            val books : List<Book> = searchResponse.documents
//            bookSearchAdapter.submitList(books)
//        })

        collectLatestStateFlow(bookSearchViewModel.searchPagingResult){
            bookSearchAdapter.submitData(it)
        }
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
                        bookSearchViewModel.searchBooksPaging(query)
                        bookSearchViewModel.query = query
                    }
                }
            }
            startTime = endtime
        }
    }

    private fun setUpLoadState() {
//         ViewHolder로 아이템에 대한 LoadState가 아닌 RV 전체에 대한 LoadState
        bookSearchAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState : LoadStates = combinedLoadStates.source
            val isListEmpty : Boolean = bookSearchAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            binding.tvEmptyList.isVisible = isListEmpty
            binding.rvSearchBooks.isVisible = !isListEmpty

            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
//
//            binding.btnRetry.isVisible = loadState.refresh is LoadState.Error
//                    || loadState.append is LoadState.Error
//                    || loadState.prepend is LoadState.Error
//
//            val errorState : LoadState.Error? = loadState.append as? LoadState.Error
//                ?: loadState.prepend as? LoadState.Error
//                ?: loadState.refresh as? LoadState.Error
//
//            errorState?.let {
//                Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        binding.btnRetry.setOnClickListener {
//            bookSearchAdapter.retry()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}