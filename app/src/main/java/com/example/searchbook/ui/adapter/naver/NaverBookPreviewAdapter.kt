package com.example.searchbook.ui.adapter.naver

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbook.R
import com.example.searchbook.data.model.Book
import com.example.searchbook.data.model.NaverBook
import com.example.searchbook.databinding.ItemNaverBookPreviewBinding

class NaverBookPreviewAdapter : ListAdapter<NaverBook, NaverBookPreviewAdapter.PreviewViewHolder>(
    BookDiffCallBack
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        val binding : ItemNaverBookPreviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_naver_book_preview, parent, false)
        return PreviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        val book = currentList[position]
        holder.bind(book)
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(book) }
        }
    }

    private var onItemClickListener : ((NaverBook) -> Unit)? = null
    fun setOnItemClickListener(listener: (NaverBook) -> Unit){
        onItemClickListener = listener
    }

    class PreviewViewHolder(
        private val binding : ItemNaverBookPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book : NaverBook){
            binding.apply {
                naverBook = book
            }
        }
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