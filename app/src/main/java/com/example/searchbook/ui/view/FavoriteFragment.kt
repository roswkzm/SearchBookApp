package com.example.searchbook.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbook.R
import com.example.searchbook.databinding.FragmentFavoriteBinding
import com.example.searchbook.ui.adapter.BookPreviewAdapter
import com.example.searchbook.ui.viewmodel.BookSearchViewModel
import com.example.searchbook.util.collectLatestStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookSearchViewModel : BookSearchViewModel
    private lateinit var bookPreviewAdapter: BookPreviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookSearchViewModel = (activity as MainActivity).viewModel

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
        collectLatestStateFlow(bookSearchViewModel.favoriteBooks){
            bookPreviewAdapter.submitList(it)
        }
    }

    private fun initUI(){
        bookPreviewAdapter = BookPreviewAdapter()
        binding.rvFavoriteBook.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = bookPreviewAdapter
        }

        // BookDetailFragment로 book 정보 넘기면서 이동
        bookPreviewAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToFragmentBookDetail(it)
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
                val position = viewHolder.adapterPosition
                val book = bookPreviewAdapter.currentList[position]
                bookSearchViewModel.deleteBook(book)
                Toast.makeText(context, "Book has Delete", Toast.LENGTH_SHORT).show()

            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavoriteBook)
        }
    }
}