package com.example.lokaljobs.ui.bookmark

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
import com.example.lokaljobs.databinding.FragmentBookmarkBinding
import com.example.lokaljobs.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val viewModel: BookmarkViewModel by activityViewModels()
    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var adapter: JobsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        adapter = JobsAdapter(
            onClickListener = { result ->
                viewModel.selectJob(result)
                findNavController().navigate(R.id.action_bookmarkFragment_to_detailFragment)
            },
            onBookmarkClickListener = { result, isBookmarked ->
                viewModel.toggleBookmark(
                    result,
                    isBookmarked
                )
            }
        )
        binding.itemRecyclerView2.adapter = adapter
        lifecycleScope.launch {
            viewModel.bookmarkedJobs.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                binding.emptyStateView.visibility = View.VISIBLE
                binding.itemRecyclerView2.visibility = View.GONE
            } else {
                binding.emptyStateView.visibility = View.GONE
                binding.itemRecyclerView2.visibility = View.VISIBLE
            }

            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        (requireActivity() as MainActivity).checkInternetConnection()
        return binding.root
    }


}

