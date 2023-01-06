package com.example.searchbook.ui.adapter.naver

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbook.data.model.NaverBook
import com.example.searchbook.databinding.ItemNaverBookPreviewBinding

class NaverBookSearchPagingAdapter : PagingDataAdapter<NaverBook, NaverBookSearchPagingAdapter.BookSearchViewHolder>(
    BookDiffCallBack) {

    class BookSearchViewHolder(
        private val binding : ItemNaverBookPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book : NaverBook){
            binding.apply {
                naverBook = book
            }
        }
    }

    override fun onBindViewHolder(holder: BookSearchViewHolder, position: Int) {
        val pagedBook = getItem(position)
        pagedBook?.let { book ->
            holder.bind(book)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(book) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookSearchViewHolder {
        return BookSearchViewHolder(ItemNaverBookPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    private var onItemClickListener : ((NaverBook) -> Unit)? = null
    fun setOnItemClickListener(listener: (NaverBook) -> Unit){
        onItemClickListener = listener
    }

    companion object{
        private val BookDiffCallBack = object  : DiffUtil.ItemCallback<NaverBook>(){
            override fun areItemsTheSame(oldItem: NaverBook, newItem: NaverBook): Boolean {
                return oldItem.isbn == newItem.isbn
            }

            override fun areContentsTheSame(oldItem: NaverBook, newItem: NaverBook): Boolean {
                return oldItem == newItem
            }
        }
    }
}