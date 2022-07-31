package com.example.searchbook.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.searchbook.R
import com.example.searchbook.databinding.FragmentBookDetailBinding
import com.example.searchbook.databinding.FragmentSearchBinding

class BookDetailFragment : Fragment() {

    private var _binding : FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<BookDetailFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val book = args.book
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(book.url)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}