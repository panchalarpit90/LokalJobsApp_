package com.example.lokaljobs.ui.overview


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.lokaljobs.R
import com.example.lokaljobs.adapter.JobsAdapter
import com.example.lokaljobs.databinding.FragmentOverviewBinding
import com.example.lokaljobs.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OverviewFragment : Fragment() {

    private val viewModel: OverviewViewModel by activityViewModels()
    private lateinit var adapter: JobsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        adapter = JobsAdapter(
            onClickListener = { result ->
                viewModel.selectJob(result)
                findNavController().navigate(R.id.action_overviewFragment_to_detailFragment)
            },
            onBookmarkClickListener = { result, isBookmarked ->
                viewModel.toggleBookmark(
                    result,
                    isBookmarked
                )
            }
        )

        binding.itemRecyclerView.adapter = adapter
        adapter.addLoadStateListener { state ->

            when (state.refresh) {
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.GONE
                }

                is LoadState.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }

        }
        lifecycleScope.launch {
            viewModel.list.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
        (requireActivity() as MainActivity).checkInternetConnection()

        return binding.root
    }
}
