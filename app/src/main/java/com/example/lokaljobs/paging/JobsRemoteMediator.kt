package com.example.lokaljobs.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.lokaljobs.network.apiinterface.ApiInterface
import com.example.lokaljobs.network.model.Result
import com.example.lokaljobs.room.JobsDao
import com.example.lokaljobs.room.ResultRemoteKey

@ExperimentalPagingApi
class JobsRemoteMediator(
    private val jobsDao: JobsDao,
    private val apiInterface: ApiInterface,
    private val initialPage: Int = 1
) : RemoteMediator<Int, Result>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.APPEND -> {
                    val remoteKey = getLastKey(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey.next ?: return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.REFRESH -> {
                    val remoteKey = getClosestKey(state)
                    remoteKey?.prev ?: initialPage
                }
            }

            val response = apiInterface.getNewJobs(page)
            val endOfPagination = response.body()?.results?.isEmpty() ?: true

            if (response.isSuccessful) {
                response.body()?.let {
                    if (loadType == LoadType.REFRESH) {
                        jobsDao.deleteNonBookmarkedJobs()
                        jobsDao.deleteAllRemoteKeys()
                    }
                    val prev = if (page == initialPage) null else page - 1
                    val next = if (endOfPagination) null else page + 1
                    val keys = it.results!!.map { result ->
                        ResultRemoteKey(result.id!!, prev, next)
                    }

                    jobsDao.insertAllRemoteKey(keys)
                    jobsDao.insertAllJobs(it.results!!)
                }
                MediatorResult.Success(endOfPaginationReached = endOfPagination)
            } else {
                MediatorResult.Success(endOfPaginationReached = true)
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getClosestKey(state: PagingState<Int, Result>): ResultRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { item ->
                jobsDao.getAllRemoteKey(item.id)
            }
        }
    }

    private suspend fun getLastKey(state: PagingState<Int, Result>): ResultRemoteKey? {
        return state.lastItemOrNull()?.let { item ->
            jobsDao.getAllRemoteKey(item.id)
        }
    }
}

