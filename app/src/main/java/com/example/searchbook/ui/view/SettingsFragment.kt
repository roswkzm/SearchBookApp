package com.example.searchbook.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.searchbook.R
import com.example.searchbook.databinding.FragmentSettingsBinding
import com.example.searchbook.ui.viewmodel.BookSearchViewModel
import com.example.searchbook.util.Sort
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookSearchViewModel : BookSearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookSearchViewModel = (activity as MainActivity).viewModel

        saveSettings()
        loadSettings()
    }

    private fun saveSettings(){
        binding.rgSort.setOnCheckedChangeListener{ _, checkedId ->
            val value = when (checkedId){
                R.id.rb_accuracy -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            }
            bookSearchViewModel.saveSortMode(value)
        }
    }

    private fun loadSettings(){
        lifecycleScope.launch {
            val buttonId : Int = when (bookSearchViewModel.getSortMode()){
                Sort.ACCURACY.value ->  R.id.rb_accuracy
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }
            binding.rgSort.check(buttonId)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}