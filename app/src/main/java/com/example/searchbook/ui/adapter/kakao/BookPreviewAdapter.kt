package com.example.searchbook.ui.adapter.kakao

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbook.R
import com.example.searchbook.data.model.Book
import com.example.searchbook.databinding.ItemBookPreviewBinding

class BookPreviewAdapter : ListAdapter<Book, BookPreviewAdapter.BookPreviewViewHolder>(
    BookDiffCallBack
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookPreviewViewHolder {
        val binding : ItemBookPreviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_book_preview, parent, false)
        return BookPreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookPreviewViewHolder, position: Int) {
        val book = currentList[position]
        holder.bind(book)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(book) }
        }
    }

    private var onItemClickListener : ((Book) -> Unit)? = null
    fun setOnItemClickListener(listener: (Book) -> Unit){
        onItemClickListener = listener
    }

    class BookPreviewViewHolder(
        private val binding : ItemBookPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(book : Book){
            binding.apply {
                bookInfo = book
            }
        }
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