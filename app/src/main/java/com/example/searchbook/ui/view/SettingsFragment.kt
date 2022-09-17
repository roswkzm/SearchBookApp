package com.example.searchbook.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.searchbook.R
import com.example.searchbook.databinding.FragmentSettingsBinding
import com.example.searchbook.ui.viewmodel.SettingsViewModel
import com.example.searchbook.util.Sort
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveSettings()
        loadSettings()
        showWorkStatus()
    }

    private fun saveSettings(){
        binding.rgSort.setOnCheckedChangeListener{ _, checkedId ->
            val value = when (checkedId){
                R.id.rb_accuracy -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            }
            settingsViewModel.saveSortMode(value)
        }

        // WorkManager
        binding.swCacheDelete.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.saveCacheDeleteMode(isChecked)
            if (isChecked){
                settingsViewModel.setWork()
            } else {
                settingsViewModel.deleteWork()
            }
        }
    }

    private fun loadSettings(){
        lifecycleScope.launch {
            val buttonId : Int = when (settingsViewModel.getSortMode()){
                Sort.ACCURACY.value ->  R.id.rb_accuracy
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }
            binding.rgSort.check(buttonId)
        }

        // WorkManager
        lifecycleScope.launch {
            val mode = settingsViewModel.getCacheDeleteMode()
            binding.swCacheDelete.isChecked = mode
        }
    }

    private fun showWorkStatus() {
        settingsViewModel.getWorkStatus().observe(viewLifecycleOwner) { workInfo ->
            Log.d("WorkManager", workInfo.toString())
            if (workInfo.isEmpty()){
                binding.tvWorkStatus.text = "No works"
            } else {
                binding.tvWorkStatus.text = workInfo[0].state.toString()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}