package com.example.searchbook.ui.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.searchbook.R
import com.example.searchbook.databinding.FragmentBookDetailBinding
import com.example.searchbook.ui.viewmodel.kakao.BookDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment() {

    private var _binding : FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<BookDetailFragmentArgs>()
    private val bookDetailViewModel by viewModels<BookDetailViewModel>()

    private val mWebView by lazy { binding.webView }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kakaoBook = args.book
        val naverBook = args.naverBook

        if (kakaoBook != null){
            mWebView.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadUrl(kakaoBook.url)
            }

            binding.btnFavorite.setOnClickListener {
                bookDetailViewModel.saveBook(kakaoBook)
                Toast.makeText(context, "Favorite Book Save", Toast.LENGTH_SHORT).show()
            }
        } else {
            mWebView.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadUrl(naverBook!!.link)
            }

            binding.btnFavorite.setOnClickListener {
                bookDetailViewModel.saveNaverBook(naverBook!!)
                Toast.makeText(context, "Favorite Book Save", Toast.LENGTH_SHORT).show()
            }
        }

        backPressEvent()


    }

    private fun backPressEvent(){
        mWebView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_BACK -> if (mWebView.canGoBack()) {
                        mWebView.goBack()
                        return@setOnKeyListener true
                    }
                }
            }
            false
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}