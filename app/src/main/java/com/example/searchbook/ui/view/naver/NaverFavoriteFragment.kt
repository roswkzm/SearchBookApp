package com.example.searchbook.ui.view.naver

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
import com.example.searchbook.databinding.FragmentNaverFavoriteBinding
import com.example.searchbook.repository.naver.NaverBookSearchRepository
import com.example.searchbook.ui.adapter.naver.NaverBookPreviewAdapter
import com.example.searchbook.ui.viewmodel.naver.NaverFavoriteViewModel
import com.example.searchbook.util.collectLatestStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NaverFavoriteFragment : Fragment() {

    private var _binding : FragmentNaverFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel by viewModels<NaverFavoriteViewModel>()
    private lateinit var favoriteAdapter : NaverBookPreviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_naver_favorite, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        setupTouchHelper(view)

        collectLatestStateFlow(favoriteViewModel.favoriteBooks){
            favoriteAdapter.submitList(it)
        }
    }

    private fun initUi(){
        favoriteAdapter = NaverBookPreviewAdapter()
        binding.rvFavoriteBook.apply {
            adapter = favoriteAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        favoriteAdapter.setOnItemClickListener {
            val action = NaverFavoriteFragmentDirections.actionFragmentNaverFavoriteToBookDetailFragment(null, it)
            findNavController().navigate(action)
        }
    }

    private fun setupTouchHelper(view : View){
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
                val book = favoriteAdapter.currentList[position]
                favoriteViewModel.deleteBook(book)
                Toast.makeText(context, "Book has Delete", Toast.LENGTH_SHORT).show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavoriteBook)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}