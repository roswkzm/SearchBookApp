package com.example.searchbook.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbook.R
import com.example.searchbook.databinding.FragmentFavoriteBinding
import com.example.searchbook.ui.adapter.BookSearchPagingAdapter
import com.example.searchbook.ui.viewmodel.FavoriteViewModel
import com.example.searchbook.util.collectLatestStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private lateinit var bookSearchAdapter: BookSearchPagingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setupTouchHelper(view)
//        // StateFlow 구독
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
//                bookSearchViewModel.favoriteBooks.collectLatest {
//                    bookPreviewAdapter.submitList(it)
//                }
//            }
//        }

        // 확장 함수로 사용
        collectLatestStateFlow(favoriteViewModel.favoritePagingBooks){
            bookSearchAdapter.submitData(it)
        }
    }

    private fun initUI(){
        bookSearchAdapter = BookSearchPagingAdapter()
        binding.rvFavoriteBook.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = bookSearchAdapter
        }

        // BookDetailFragment로 book 정보 넘기면서 이동
        bookSearchAdapter.setOnItemClickListener {
            val action = FavoriteFragmentDirections.actionFragmentFavoriteToFragmentBookDetail(it)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupTouchHelper(view: View){
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
//                val book = bookSearchAdapter.currentList[position]
//                bookSearchViewModel.deleteBook(book)
//                Toast.makeText(context, "Book has Delete", Toast.LENGTH_SHORT).show()
                val pagedBook = bookSearchAdapter.peek(position)
                pagedBook?.let { book ->
                    favoriteViewModel.deleteBook(book)
                    Toast.makeText(context, "Book has Delete", Toast.LENGTH_SHORT).show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavoriteBook)
        }
    }
}