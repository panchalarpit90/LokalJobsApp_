package com.example.lokaljobs.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.lokaljobs.network.model.Result
import com.example.lokaljobs.repository.GetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val getRepository: GetRepository
) : ViewModel() {

    val list: Flow<PagingData<Result>> = getRepository.getAllJobs()

    private val _selectedJob = MutableStateFlow<Result?>(null)
    val selectedJob: StateFlow<Result?> = _selectedJob

    fun selectJob(result: Result) {
        _selectedJob.value = result
    }

    fun toggleBookmark(result: Result, isBookmarked: Boolean) {
        viewModelScope.launch {
            getRepository.updateBookmarkStatus(result.id, isBookmarked)
        }
    }
}

