package com.example.searchbook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.searchbook.R
import com.example.searchbook.databinding.ItemLoadStateBinding

class BookSearchLoadStateAdapter(
    private val retry : () -> Unit
) : LoadStateAdapter<BookSearchLoadStateAdapter.BookSearchLoadStateViewHolder>(){

    override fun onBindViewHolder(holder: BookSearchLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BookSearchLoadStateViewHolder {
        val binding : ItemLoadStateBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_load_state, parent, false)
        return BookSearchLoadStateViewHolder(binding, retry)
    }

    class BookSearchLoadStateViewHolder(
        private val binding: ItemLoadStateBinding,
        retry : () -> Unit
    ) : RecyclerView.ViewHolder(binding.root){

        init {
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState : LoadState){
            if (loadState is LoadState.Error){
                binding.tvError.text = loadState.error.localizedMessage
            }

            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.tvError.isVisible = loadState is LoadState.Error
            binding.btnRetry.isVisible = loadState is LoadState.Error
        }
    }
}