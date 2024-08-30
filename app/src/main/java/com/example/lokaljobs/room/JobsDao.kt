package com.example.lokaljobs.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lokaljobs.network.model.Result


@Dao
interface JobsDao {

    @Query("SELECT * FROM Result WHERE is_bookmarked = 1")
    fun getBookmarkedJobs(): PagingSource<Int, Result>

    @Query("UPDATE Result SET is_bookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmarkStatus(id: Int, isBookmarked: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllJobs(list: List<Result>)

    @Query("Select * From Result")
    fun getAllJobs(): PagingSource<Int, Result>

    @Query("DELETE FROM Result")
    suspend fun deleteAllJobs()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKey(list: List<ResultRemoteKey>)

    @Query("SELECT * FROM ResultRemoteKey WHERE id = :id")
    suspend fun getAllRemoteKey(id: kotlin.Int): ResultRemoteKey?

    @Query("DELETE FROM ResultRemoteKey")
    suspend fun deleteAllRemoteKeys()

    @Query("DELETE FROM Result WHERE is_bookmarked = 0")
    suspend fun deleteNonBookmarkedJobs()

}
