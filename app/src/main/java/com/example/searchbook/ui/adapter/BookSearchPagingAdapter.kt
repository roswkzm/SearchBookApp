package com.example.searchbook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbook.R
import com.example.searchbook.data.model.Book
import com.example.searchbook.databinding.ItemBookPreviewBinding

class BookSearchPagingAdapter : PagingDataAdapter<Book, BookSearchPagingAdapter.BookPagingViewHolder>(BookDiffCallBack) {

    override fun onBindViewHolder(holder: BookPagingViewHolder, position: Int) {
        val pagedBook = getItem(position)
        pagedBook?.let { book ->
            holder.bind(book)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(book) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookPagingViewHolder {
        val binding : ItemBookPreviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_book_preview, parent, false)
        return BookPagingViewHolder(binding)
    }

    class BookPagingViewHolder(
        private val binding : ItemBookPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(book : Book){
            binding.apply {
                bookInfo = book
            }
        }
    }

    private var onItemClickListener : ((Book) -> Unit)? = null
    fun setOnItemClickListener(listener: (Book) -> Unit){
        onItemClickListener = listener
    }

    companion object{
        private val BookDiffCallBack = object : DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.isbn == newItem.isbn
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

        }
    }
}