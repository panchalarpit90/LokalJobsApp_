package com.example.lokaljobs.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.lokaljobs.network.apiinterface.ApiInterface
import com.example.lokaljobs.network.model.Result
import com.example.lokaljobs.paging.JobsRemoteMediator
import com.example.lokaljobs.room.JobsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val jobsDao: JobsDao
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getAllJobs(): Flow<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 1,
            prefetchDistance = 1,
            initialLoadSize = 20,
            enablePlaceholders = false
        ),
        remoteMediator = JobsRemoteMediator(jobsDao, apiInterface, 1),
        pagingSourceFactory = { jobsDao.getAllJobs() }
    ).flow.map { pagingData ->
        pagingData.filter { result ->
            result.company_name != null && result.title!!.isNotEmpty()
        }
    }

    fun getBookmarkedJobs(): Flow<PagingData<Result>> = Pager(
        config = PagingConfig(
            pageSize = 1,
            prefetchDistance = 1,
            initialLoadSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { jobsDao.getBookmarkedJobs() }
    ).flow

    suspend fun updateBookmarkStatus(id: Int, isBookmarked: Boolean) {
        jobsDao.updateBookmarkStatus(id, isBookmarked)
    }
}
