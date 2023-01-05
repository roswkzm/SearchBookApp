package com.example.searchbook.ui.view.naver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.searchbook.R
import com.example.searchbook.databinding.FragmentNaverSettingsBinding
import com.example.searchbook.ui.viewmodel.naver.NaverSettingsViewModel
import com.example.searchbook.util.NaverSort
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NaverSettingsFragment : Fragment() {

    private var _binding : FragmentNaverSettingsBinding? = null
    private val binding get() = _binding!!
    private val settingsViewModel by viewModels<NaverSettingsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_naver_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSortMode()
        getSortMode()
    }

    private fun setSortMode(){
        binding.rgSort.setOnCheckedChangeListener { _, checkedId ->
            val value =
                when(checkedId){
                    R.id.rb_sim -> NaverSort.SIM.value
                    R.id.rb_date -> NaverSort.DATE.value
                    else -> return@setOnCheckedChangeListener
                }
            settingsViewModel.setSortMode(value)
        }
    }

    private fun getSortMode(){
        lifecycleScope.launch {
            val buttonId : Int = when(settingsViewModel.getSortMode()){
                NaverSort.SIM.value -> R.id.rb_sim
                NaverSort.DATE.value -> R.id.rb_date
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