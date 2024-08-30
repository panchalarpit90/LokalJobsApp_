package com.example.lokaljobs.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.lokaljobs.databinding.FragmentDetailBinding
import com.example.lokaljobs.ui.bookmark.BookmarkViewModel
import com.example.lokaljobs.ui.main.MainActivity
import com.example.lokaljobs.ui.overview.OverviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: OverviewViewModel by activityViewModels()
    private val viewModel2: BookmarkViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater)
        lifecycleScope.launch {
            viewModel.selectedJob.collectLatest { result ->
                result?.let {
                    binding.result = it
                    binding.jobtag = it.job_tags?.firstOrNull()
                    binding.primary = it.primary_details
                    binding.contact = it.contact_preference
                    binding.contentV3 = it.contentV3
                }
            }
        }
        lifecycleScope.launch {
            viewModel2.selectedJob.collectLatest { result ->
                result?.let {
                    binding.result = it
                    binding.jobtag = it.job_tags?.firstOrNull()
                    binding.primary = it.primary_details
                    binding.contact = it.contact_preference
                    binding.contentV3 = it.contentV3
                }
            }
        }
        binding.backBtnNav.setOnClickListener {
            findNavController().popBackStack()
        }

        (requireActivity() as MainActivity).checkInternetConnection()

        return binding.root
    }
}
